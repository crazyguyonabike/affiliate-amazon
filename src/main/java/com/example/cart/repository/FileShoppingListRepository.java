package com.example.cart.repository;

import java.io.*;

import com.example.cart.domain.ShoppingList;
import com.example.cart.domain.Item;

import org.apache.log4j.Logger;

public class FileShoppingListRepository implements ShoppingListRepository {
    private Logger logger = Logger.getLogger(FileShoppingListRepository.class);

    private String directoryPath;

    public ShoppingList getShoppingList(final String id) {
        ShoppingList shoppingList = new ShoppingList();
        try {
            File directory = new File(directoryPath);
            File [] files = directory.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return pathname.getName().equals(id + ".lst");
                    }
                });

            if (files.length == 1)
                processFileToShoppingList(files[0], shoppingList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return shoppingList;
    }

    private void processFileToShoppingList(File file, ShoppingList shoppingList) throws Exception {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            String [] parts = line.split(",");
            shoppingList.addItem(new Item(parts[0], parts[1], Long.parseLong(parts[2])));
        }
        bufferedReader.close();
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }
}
