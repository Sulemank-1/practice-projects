import java.io.*;
import java.util.*;

public class GymManager {
    //Data Fields
    private ArrayList<Member> members;
    private String gymName;
    private final int TOTAL_CAPACITY = 100;

    //Constructor
    public GymManager(String gymName){
         this.gymName = gymName;
         members = new ArrayList<>();
    }

    //Getter
    public String getGymName() {
        return gymName;
    }

    //Methods

    //Add a member to gym
    public void addMember(Member m){
         if (members.size() >= TOTAL_CAPACITY)
             System.out.println("Members full");
         else {
             for (Member member : members)
                 if (member.getMemberID() == m.getMemberID()) {
                     System.out.println("Member already exists");
                     return;
                 }

             members.add(m);
         }
     }

     //Remove a member from gym
    public boolean removeMember(int memberID){
         for (Member member: members)
             if (member.getMemberID() == memberID){
                 members.remove(member);
                 return true;
             }

         return false;
    }

    //Find a member of gym
    public Member findMember(int memberID){
        for (Member member: members)
            if (member.getMemberID() == memberID)
                return member;

        return null;
    }

    //Calculate total revenue
    public double calculateTotalRevenue(){
         return members.stream()
                 .mapToDouble(Member::calculateMonthlyDues)
                 .sum();
    }

    //Check member size
    public int getMemberCount(){
         return members.size();
    }

    //Display list of all members
    public void displayAllMembers(){
         members.stream()
                 .sorted()
                 .forEach(System.out::println);
    }

    //Save members list to file
    public void saveMembersToFile(String filename) {
        StorageEngine.saveData(filename, members);
    }

    //Load members list from file
    public void loadMembersFromFile(String filename) {
        members = StorageEngine.loadData(filename);
    }

}
