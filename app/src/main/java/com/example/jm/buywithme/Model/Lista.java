package com.example.jm.buywithme.Model;

import java.util.ArrayList;
import java.util.List;
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

    public Lista(){
        //Cambiado!!!
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


    public void save(Integer name, Integer namew, Integer id, String section){
        arrayw.add(namew);
        array.add(name);
        this.id.add(id);
        this.section.add(section);

    }

    public boolean delete(int position){
        try{
            arrayw.remove(position);
            array.remove(position);
            id.remove(position);
            section.remove(position);
        }catch(Exception e){

            e.printStackTrace();
            return false;
        }

        return false;
    }

    public boolean delete(Integer name, Integer namew, Integer id, String section){
        try{
            arrayw.remove(namew);
            array.remove(name);
            this.id.remove(id);
            this.section.remove(section);
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
                //array, arrayw, id, section
                sec.save(array.get(i), arrayw.get(i), id.get(i), section);
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
}
