import javax.swing.*;
import java.time.LocalDate;

/** New shared-data panel built around Johnson's unchanged breeder model and manager. */
public class BreederSalesFrame_Kilpatrick extends TablePanel_Kilpatrick {
    public BreederSalesFrame_Kilpatrick(FarmController_Kilpatrick controller) {
        super(controller,"Local Breeder Resales","One record per specialty animal. Sold animals stay in the history and cannot be sold again.",
                "ID","Animal","Breed","Breeder","Contact","Acquired","Cost","Sale price","Status");
        actions.add(Ui_Kilpatrick.button("Add Breeder Animal",this::addAnimal));
        actions.add(Ui_Kilpatrick.button("Change Asking Price",this::price));
        actions.add(Ui_Kilpatrick.button("Sell Breeder Animal",this::sell));
        refresh();
    }
    public void refresh() {
        Ui_Kilpatrick.model(table).setRowCount(0);
        for(Breeder_Johnson original:controller.data().breeders.getAllAnimals()) {
            BreederAnimal_Kilpatrick a=(BreederAnimal_Kilpatrick)original;
            Ui_Kilpatrick.model(table).addRow(new Object[]{a.getId(),a.getAnimalType(),a.getBreed(),a.getBreederName(),a.getContactInfo(),
                    a.getDateAcquired(),Validation_Kilpatrick.money(a.getCostCents()),Validation_Kilpatrick.money(a.getPriceCents()),a.isSold()?"SOLD":"Available"});
        }
        summary.setText(controller.data().breeders.getAvailableAnimals().size()+" available | Sold-animal margin (sale price minus breeder cost): "+
                Validation_Kilpatrick.money(controller.data().breeders.getProfitCents()));
    }
    private void addAnimal() {
        Ui_Kilpatrick.Form f=new Ui_Kilpatrick.Form();
        JTextField name=f.text("Breeder name",""); JTextField contact=f.text("Breeder contact","");
        JTextField type=f.text("Animal type",""); JTextField breed=f.text("Breed","");
        JTextField cost=f.text("Our purchase cost ($)","0.00"); JTextField price=f.text("Asking price ($)","0.00");
        JTextField date=f.text("Acquired (MM/DD/YYYY)",LocalDate.now().format(Validation_Kilpatrick.DATE));
        if(!f.show(this,"Add specialty animal from a breeder"))return;
        long costCents=Validation_Kilpatrick.cents(cost.getText()), priceCents=Validation_Kilpatrick.cents(price.getText());
        String acquired=Validation_Kilpatrick.date(date.getText()).format(Validation_Kilpatrick.DATE);
        controller.commit(data->data.breeders.addAnimal(name.getText(),contact.getText(),type.getText(),breed.getText(),costCents,priceCents,acquired));
    }
    private void price() {
        int id=Ui_Kilpatrick.selectedId(table);
        BreederAnimal_Kilpatrick a=controller.data().breeders.findById(id);
        if(a.isSold())throw new IllegalArgumentException("A sold animal's price cannot change.");
        Ui_Kilpatrick.Form f=new Ui_Kilpatrick.Form(); JTextField price=f.text("New asking price ($)",Validation_Kilpatrick.dollars(a.getPriceCents()));
        if(!f.show(this,"Update price for breeder animal #"+id))return;
        long cents=Validation_Kilpatrick.cents(price.getText());
        controller.commit(data->data.breeders.findById(id).setPrice(cents/100.0));
    }
    private void sell() {
        int id=Ui_Kilpatrick.selectedId(table);
        BreederAnimal_Kilpatrick a=controller.data().breeders.findById(id);
        if(a.isSold())throw new IllegalArgumentException("This animal has already been sold.");
        Ui_Kilpatrick.Form f=new Ui_Kilpatrick.Form();
        f.add("Animal",new JLabel(a.getBreed()+" "+a.getAnimalType()));
        f.add("Total payment",new JLabel(Validation_Kilpatrick.money(a.getPriceCents())));
        JTextField customer=f.text("Customer (optional)","");
        JComboBox<String> method=Ui_Kilpatrick.methods();f.add("Payment received by",method);
        if(!f.show(this,"Record breeder-animal sale and payment"))return;
        StoreSale_Kilpatrick[] result=new StoreSale_Kilpatrick[1];
        controller.commit(data->result[0]=data.sellBreederAnimal(id,customer.getText(),(String)method.getSelectedItem()));
        Ui_Kilpatrick.message(this,"Sale saved",result[0].getReceipt());
    }
}
