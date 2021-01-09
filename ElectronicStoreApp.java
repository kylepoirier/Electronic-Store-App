import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.*;
import java.util.*;

public class ElectronicStoreApp extends Application {
    private ElectronicStore model;
    private ElectronicStoreView view;

    public void start(Stage primaryStage) {
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView(model);

        view.getStoreStockList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update();
            }
        });

        view.getCurrCartList().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                view.update();
            }
        });

        view.getAddCartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleAddCart();
            }
        });

        view.getRemoveCartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleRemoveCart();
            }
        });

        view.getCompleteSaleButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleCompleteSale();
            }
        });

        view.getResetStoreButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleResetStore();
            }
        });

        primaryStage.setTitle("Electronic Store Application - " + model.getName()); // Set window title
        primaryStage.setScene(new Scene(view, 800, 400)); // Set size of window
        primaryStage.setResizable(false); //Disable Resize
        primaryStage.show();

        view.update();
    }


    public static void main(String[] args) {
        launch(args);
    }

    //Add Product to Current Cart
    public void handleAddCart() {
        String addCart = view.getStoreStockList().getSelectionModel().getSelectedItem().toString();

        view.getCurrCartList().getItems().add(addCart);

        view.getCompleteSaleButton().setDisable(false);

        //Keep track of Stock Quantity, remove Item from StoreStock once Quantity = 0
        for (Product i: model.getStock()) {
            if (i != null) {
                if (i.toString().equals(addCart)) {
                    i.removeStockQuantity(1);

                    //System.out.println(i.getStockQuantity());

                    view.addCurrCartMoney(i.getPrice());
                    //System.out.println(view.getCurrCartMoney());

                    i.addSoldQuantity(1);


                    if (i.getStockQuantity() == 0) {
                     view.getStoreStockList().getItems().remove(addCart);
                     }
                }
            }
        }

        view.update();
    }

        //Remove Product from Current Cart
        public void handleRemoveCart() {
            String removeCart = view.getCurrCartList().getSelectionModel().getSelectedItem().toString();
            view.getCurrCartList().getItems().remove(removeCart);

            //Keep track of Stock Quantity, add Item to Store Stock once Stock Quantity = 1
            for (Product i: model.getStock()) {
                if (i != null) {
                    if (i.toString().equals(removeCart)) {
                        i.addStockQuantity(1);

                        //System.out.println(i.getStockQuantity());

                        view.removeCurrCartMoney(i.getPrice());
                        //System.out.println(view.getCurrCartMoney());

                        i.removeSoldQuantity(1);

                        if (i.getStockQuantity() == 1) {
                            view.getStoreStockList().getItems().add(removeCart);
                        }
                        }
                    }
                }

            view.update();
        }

        //Complete the Sale
        public void handleCompleteSale() {
        //Clear
        view.getPopItemsList().getItems().clear();
        //Disable button
            view.getCompleteSaleButton().setDisable(true);
            //Add to the revenue
            view.addRevenue(view.getCurrCartMoney());
            //Clear everything within Current Cart
            view.getCurrCartList().getItems().clear();
            view.removeCurrCartMoney(view.getCurrCartMoney());
            //Add to the number of sales
            view.addNumSales(1);
            //Add to $ / sale
            view.getMoney();

            //Add MostPopularItems
            Product[] highestSold = new Product[3];
            int highestSoldInt = 0;

            Product[] stock = model.getStock();
            Product[] stockPop = new Product[model.getCurProducts()];

            for (int i = 0; i < stockPop.length; i++) {
                stockPop[i] = stock[i];
            }

            for (int i = 0; i < stockPop.length-1; i++) {
                for (int j = 0; j < stockPop.length - i - 1; j++)
                    if (stockPop[j] != null && stockPop[j+1] != null) {
                        if (stockPop[j].getSoldQuantity() < stockPop[j + 1].getSoldQuantity()) {
                            Product temp = stockPop[j];
                            stockPop[j] = stockPop[j + 1];
                            stockPop[j + 1] = temp;
                        }
                    }

                //view.getPopItemsList().getItems().add(stockPop[i]);

                }
            for (int i = 0; i < 3; i++) {
                highestSold[i] = stockPop[i];
                view.getPopItemsList().getItems().add(highestSold[i]);
            }


            for (int i = 0; i<stockPop.length;i++){
            System.out.println(stockPop[i].toString()+"---"+stockPop[i].getSoldQuantity());}
            System.out.println("--------------------------------------------");

            view.update();
    }

        //Reset the Store
        public void handleResetStore() {
        //Reset CurrCartMoney
            view.resetCurrCartMoney();
        //Reset View Lists
            view.getStoreStockList().getItems().clear();
            view.getCurrCartList().getItems().clear();
            view.getPopItemsList().getItems().clear();
        //Reset TextFields
            view.resetMoney();
            view.resetRevenue();
            view.resetSales();
        //Reset PopItems
            view.getPopItemsList().getItems().add(model.getStock()[0].toString());
            view.getPopItemsList().getItems().add(model.getStock()[1].toString());
            view.getPopItemsList().getItems().add(model.getStock()[2].toString());
        //Reset values of Store by creating new Store
            model = ElectronicStore.createStore();
            Product[] stock = model.getStock();
            String[] stockName = new String[model.getCurProducts()];
            for (int i = 0; i < stockName.length; i++) {
                stockName[i] = stock[i].toString();
            }
            view.getStoreStockList().setItems(FXCollections.observableArrayList(stockName));



            view.update();
        }

    }