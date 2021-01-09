import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;


public class ElectronicStoreView extends Pane {
    //Initialize
    private double currCartMoney, revenue;
    private int numSales;
    private ElectronicStore model;
    private ListView<String> popItemsList, storeStockList, currCartList;
    private TextField numSalesField, revenueField, moneyField;
    private Button resetStoreButton, addCartButton, removeCartButton, completeSaleButton;
    private Label storeSumLabel, storeStockLabel, currCartLabel, numSalesLabel, revenueLabel, moneySalesLabel, mostPopLabel;

    public ElectronicStoreView(ElectronicStore iModel) {
        model = iModel;


        //Add Labels
        storeSumLabel = new Label("Store Summary:");
        storeStockLabel = new Label("Store Stock:");
        currCartLabel = new Label("Current Cart:($" + String.format("%.2f",currCartMoney) + ")");
        numSalesLabel = new Label("# Sales:");
        revenueLabel = new Label("Revenue:");
        moneySalesLabel = new Label("$ / Sale:");
        mostPopLabel = new Label("Most Popular Items:");

        //Edit Labels
        storeSumLabel.relocate(45, 25);
        storeStockLabel.relocate(340, 25);
        currCartLabel.relocate(610, 25);
        numSalesLabel.relocate(25, 55);
        revenueLabel.relocate(25, 90);
        moneySalesLabel.relocate(25, 125);
        mostPopLabel.relocate(45, 157);

        //Add List
        popItemsList = new ListView<>();
        storeStockList = new ListView<>();
        currCartList = new ListView<>();

        popItemsList.getItems().add(model.getStock()[0].toString());
        popItemsList.getItems().add(model.getStock()[1].toString());
        popItemsList.getItems().add(model.getStock()[2].toString());

        //Edit List
        popItemsList.relocate(25, 175);
        popItemsList.setPrefSize(165, 160);

        storeStockList.relocate(260, 60);
        storeStockList.setPrefSize(250, 275);

        currCartList.relocate(520, 60);
        currCartList.setPrefSize(250, 275);

        //Add TextField
        numSalesField = new TextField("0");
        revenueField = new TextField("0.00");
        moneyField = new TextField("N/A");

        //Edit TextField
        numSalesField.relocate(95, 50);
        numSalesField.setPrefSize(100, 10);
        numSalesField.setEditable(false);

        revenueField.relocate(95, 85);
        revenueField.setPrefSize(100, 10);
        revenueField.setEditable(false);

        moneyField.relocate(95, 120);
        moneyField.setPrefSize(100, 10);
        moneyField.setEditable(false);

        //Add Button
        resetStoreButton = new Button("Reset Store");
        addCartButton = new Button("Add to Cart");
        removeCartButton = new Button("Remove from Cart");
        completeSaleButton = new Button("Complete Sale");

        //Edit Button
        resetStoreButton.relocate(45, 340);
        addCartButton.relocate(320, 340);
        removeCartButton.relocate(530, 340);
        completeSaleButton.relocate(650, 340);

        resetStoreButton.setPrefSize(120, 45);
        addCartButton.setPrefSize(120, 45);
        removeCartButton.setPrefSize(120, 45);
        completeSaleButton.setPrefSize(120, 45);

        addCartButton.setDisable(true);
        removeCartButton.setDisable(true);
        completeSaleButton.setDisable(true);

        //Add all Lists
        getChildren().addAll(popItemsList, storeStockList, currCartList);
        //Add all Labels
        getChildren().addAll(storeSumLabel, storeStockLabel, currCartLabel, numSalesLabel, revenueLabel, moneySalesLabel, mostPopLabel);
        //Add all TextFields
        getChildren().addAll(numSalesField, revenueField, moneyField);
        //Add all Buttons
        getChildren().addAll(resetStoreButton, addCartButton, removeCartButton, completeSaleButton);

        //Set storeStockList
        Product[] stock = model.getStock();
        String[] stockName = new String[model.getCurProducts()];

        for (int i = 0; i < stockName.length; i++) {
            stockName[i] = stock[i].toString();
        }

        storeStockList.setItems(FXCollections.observableArrayList(stockName));

        update();


    }

    public void addNumSales(int i) {numSalesField.setText("" + (numSales += i));}

    public void resetSales() {
        numSales = 0;
        numSalesField.setText("" + numSales);
    }

    public void addRevenue(double i) {revenueField.setText("" + String.format("%.2f",revenue += i));}

    public void resetRevenue() {
        revenue = 0;
        revenueField.setText("" + String.format("%.2f",revenue));
    }


    public void getMoney() {
         moneyField.setText("" + String.format("%.2f", (revenue / numSales)));
    }

    public void resetMoney() {
        moneyField.setText("N/A");
    }

    public Button getResetStoreButton() {
        return resetStoreButton;
    }

    public Button getAddCartButton() {
        return addCartButton;
    }

    public Button getRemoveCartButton() {
        return removeCartButton;
    }

    public Button getCompleteSaleButton() {
        return completeSaleButton;
    }

    public ListView getPopItemsList() {
        return popItemsList;
    }

    public ListView getStoreStockList() {
        return storeStockList;
    }

    public ListView getCurrCartList() {
        return currCartList;
    }

    public double getCurrCartMoney() {return currCartMoney;}

    public void resetCurrCartMoney() {
        currCartMoney = 0;
    }

    public void addCurrCartMoney(double p) {currCartMoney += p;}

    public void removeCurrCartMoney(double p) {currCartMoney -= p;}


    public void update() {


        //Enable / Disable Buttons
        int storeSelection = storeStockList.getSelectionModel().getSelectedIndex();

        if (storeSelection >= 0) {
            //System.out.println(storeSelection);
            addCartButton.setDisable(false);
        }

        int cartSelection = currCartList.getSelectionModel().getSelectedIndex();

        if (cartSelection >= 0) {
            removeCartButton.setDisable(false);
        }

        if (cartSelection == -1) {
            removeCartButton.setDisable(true);
        }
        //Update the current cart label.
        currCartLabel.setText("Current Cart:($" + String.format("%.2f",currCartMoney) + ")");


    }
}