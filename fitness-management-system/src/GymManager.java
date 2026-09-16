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

    public boolean removeMember(int memberID){
         for (Member member: members)
             if (member.getMemberID() == memberID){
                 members.remove(member);
                 return true;
             }

         return false;
    }

    public Member findMember(int memberID){
        for (Member member: members)
            if (member.getMemberID() == memberID)
                return member;

        return null;
    }

    public double calculateTotalRevenue(){
         double sum = 0.0;
         for (Member member: members)
            sum += member.calculateMonthlyDues();

         return sum;
    }

    public int getMemberCount(){
         return members.size();
    }

    public void displayAllMembers(){
         ArrayList<Member> sortedList = new ArrayList<>(members);
         Collections.sort(sortedList);
         for (Member member: sortedList)
            System.out.println(member);
    }

    public void saveMembersToFile(String filename) {
        StorageEngine.saveData(filename, members);
    }

    public void loadMembersFromFile(String filename) {
        members = StorageEngine.loadData(filename);
    }

}
