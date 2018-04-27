package com.example.jm.buywithme.Model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jm on 17.12.30.
 */

public class Lista {

    private Long time;
    private String admin;
    private String nameList;
    private List<String> users = new ArrayList<>();
    private List<Integer> array = new ArrayList<>();
    private List<Integer> arrayw = new ArrayList<>();
    private List<Integer> id = new ArrayList<>();
    private List<String> section = new ArrayList<>();

    private List<String> quantity = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> owner = new ArrayList<>();
    private List<String> productName = new ArrayList<>();

    private Map<String, String> owp = new LinkedHashMap<>();

    public Lista(){
        time = 0L;
    }

    public Lista(String admin, String nameList){
        this.admin = admin;
        users.add(admin);
        this.nameList = nameList;
        time = System.nanoTime();

    }

    public void setTime(Long time){

        this.time = time;
    }

    public Long getTime(){
        return time;
    }

    public void setArray(List<Integer> array) {
        this.array = array;
    }

    public void setArrayw(List<Integer> arrayw) {
        this.arrayw = arrayw;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public List<String> getSection() {
        return section;
    }

    public void setSection(List<String> section) {
        this.section = section;
    }


    public void save(Integer name, Integer namew, Integer id, String section, String quantity, String description, String owner, String productName){
        arrayw.add(namew);
        array.add(name);
        this.id.add(id);
        this.section.add(section);

        this.quantity.add(quantity);
        this.description.add(description);
        this.owner.add(owner);
        this.productName.add(productName);
    }

    public boolean delete(int position){
        try{
            arrayw.remove(position);
            array.remove(position);
            id.remove(position);
            section.remove(position);

            this.quantity.remove(position);
            this.description.remove(position);
            this.owner.remove(position);
            this.productName.remove(position);
        }catch(Exception e){

            e.printStackTrace();
            return false;
        }

        return false;
    }

    public boolean delete(Integer name, Integer namew, Integer id, String section, String quantity, String description, String owner, String productName){
        try{
            arrayw.remove(namew);
            array.remove(name);
            this.id.remove(id);
            this.section.remove(section);

            this.quantity.remove(quantity);
            this.description.remove(description);
            this.owner.remove(owner);
            this.productName.remove(productName);
        }catch(Exception e){

            e.printStackTrace();
            return false;
        }

        return false;
    }

    public Lista getList(String section){
        Lista sec = new Lista(admin, nameList);
        for(int i=0; i<this.section.size(); i++){
            if(this.section.get(i).equals(section)){
                //array, arrayw, id, section...
                sec.save(array.get(i), arrayw.get(i), id.get(i), section, quantity.get(i), description.get(i), owner.get(i), productName.get(i));
            }
        }
        return sec;
    }

    public ArrayList<Integer> getList(){
        ArrayList<Integer> array = new ArrayList<Integer>(this.array);
        return array;

    }

    public ArrayList<Integer> getListw(){
        ArrayList<Integer> array = new ArrayList<Integer>(arrayw);
        return array;
    }

    public ArrayList<Integer> getId(){
        ArrayList<Integer> array = new ArrayList<Integer>(id);
        return array;
    }

    public int size(){
        return array.size();
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public void addUsersToTheList(String user){
        this.users.add(user);

    }

    public void deleteUsersFromTheList(String user){
        this.users.remove(user);

    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public List<String> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<String> quantity) {
        this.quantity = quantity;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }

    public List<String> getProductName() {
        return productName;
    }

    public void setProductName(List<String> productName) {
        this.productName = productName;
    }

    public void setOwp(Map<String, String> owp){
        this.owp = owp;
    }

    public Map<String, String> getOwp(){
        return  owp;
    }
}
