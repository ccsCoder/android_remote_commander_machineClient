/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firebasecommandlistener;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.Map;
import model.Command;
import utils.CommandProcessorUtil;

/**
 *
 * @author neo
 */
public class FirebaseCommandListener implements Runnable {
    
    private Firebase firebaseRef ;
    
    private void initFirebase() {
         this.firebaseRef = new Firebase("https://fiery-fire-4989.firebaseio.com/");
         System.out.println("Inited Firebase Successfully!");
         this.firebaseRef.addValueEventListener(new ValueEventListener() {

             @Override
             public void onDataChange(DataSnapshot ds) {
                 System.out.println("Change! "+ds.getValue());
                 Map<String,String> data = (Map<String,String>)ds.getValue();
//                 System.out.println(data.get("whatToExecute"));
                 Command command = new Command(data.get("whatToExecute"), null);
                 FirebaseCommandListener.this.writeResponseToFirebase(FirebaseCommandListener.this.executeCommand(command));
                 
             }

             @Override
             public void onCancelled(FirebaseError fe) {
                 System.out.println(fe.getMessage());
             }
         });
         System.out.println("Attached Value Changed Listener...");
    }
    
    private Command executeCommand(final Command command) {
        command.setResponse(CommandProcessorUtil.processCommand(command.getCommand()));
        return command;
    }
    
    private void writeResponseToFirebase(final Command command) {
        this.firebaseRef.child("responseText").setValue(command.getResponse(), new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError ferror, Firebase firebase) {
                if (ferror != null) {
                    System.out.println("OOPS!! Couldn't write to Firebase! " + ferror.getMessage());
                } else {
                    System.out.println("Write Successful.");
                }
            }
        });
    }

    

    @Override
    public void run() {
        this.initFirebase();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread fireRunner = new Thread(new FirebaseCommandListener());
        System.out.println("Staring the Fire Thread...");
        fireRunner.start();
        while(true) {}
        
    }

    
    
}
