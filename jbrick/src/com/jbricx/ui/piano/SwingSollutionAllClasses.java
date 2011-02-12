package com.jbricx.ui.piano;
//
//import java.awt.Component;
//import org.eclipse.swt.events.KeyEvent;
//import org.eclipse.swt.events.KeyListener;
////import java.swt.event.KeyEvent;
////import java.awt.event.KeyListener;
//import java.util.HashMap;
//import java.util.Map;
//import javax.swing.SwingUtilities;
//import javax.swing.WindowConstants;
//
public class SwingSollutionAllClasses {
//
///**
//* this class is responsible for maintaining a key status map
//* @author sebastian
//*
//*/
//static class InputManager implements KeyListener {
////sgurin : þe last keypressed and keyreleased events registered for each key
//Map lastKeyPressedEvents = new HashMap();
//Map lastKeyReleasedEvents = new HashMap(); 
////one for each ascii character.
//public boolean[] key_state_up = new boolean[256]; //true if pressed
//public boolean[] key_state_down = new boolean[256]; //true if not pressed 
////a string used as a buffer by widgets or other text input controls
//private String keyCache = ""; 
//public InputManager(){
//for (int i = 0; i < key_state_up.length; i++) {
//key_state_up[i]=true;
//key_state_down[i]=false;
//}
//}
//public void keyPressed(KeyEvent e) {
//int code = e.keyCode;	
////int code = e.getKeyCode();
//if( code >= 0 && code < 256 ) {
//key_state_down[code] = true;
//key_state_up[code] = false;
//lastKeyPressedEvents.put(code, e);
//}
//}
//public void keyReleased(KeyEvent e) {
//int code = e.keyCode;
////int code = e.getKeyCode();
//if( code >= 0 && code < 256 ) {
//key_state_up[code] = true;
//key_state_down[code] = false;
//lastKeyReleasedEvents.put(code, e);
//}
//}
//public void keyTyped(KeyEvent e) {
//keyCache += e.character;	
////keyCache += e.getKeyChar(); 
//}
//public boolean isKeyDown( int key ) {
//return key_state_down[key];
//} 
//public boolean isKeyUp( int key ) {
//return key_state_up[key];
//} 
//}
//
///**
//* this class is a new KeyListener adapter that corrects the default keyreleased 
//* notification policy (repeatedly notifies keypressed and keyreleased events 
//* when a key is pressed and not released for a while). 
//* 
//* note that this is a heavy listener, use only at special cases (when you need different behaviour for keyreleased event handling)
//* it uses an InputManager that mantains the key status and starts a thread that checks each key status and notify changes
//* 
//* @author sgurin
//*
//*/
//static abstract class KeyListener2 implements KeyListener {
//
//KeyChecker checkerThread ;
//public KeyListener2(Component target) {
//super(); 
//checkerThread=new KeyChecker(this, target);
//checkerThread.start();
//} 
//public void destroy() {
//checkerThread.stopChecking();
//}
//
//static class KeyChecker extends Thread implements KeyListener {
//private static final long SLEEP_AMOUNT =50;
//InputManager iman;
//Component target;
//volatile private boolean stoped;
//public boolean[] last_key_state_up=null, last_key_state_down=null;
//KeyListener2 listener;
//
//public KeyChecker(KeyListener2 listener, Component target){
//iman = new InputManager();
//this.target = target;
//this.listener = listener;
//target.addKeyListener(l)
//target.addKeyListener(iman);
//target.addKeyListener(this);
//}
//
//@Override
//public void run() { 
//last_key_state_down=cloneArray(iman.key_state_down);
//last_key_state_up = cloneArray(iman.key_state_up);
//while(!stoped) {
//try {
//Thread.sleep(SLEEP_AMOUNT);
//} catch (InterruptedException e) {
//e.printStackTrace();
//}
//for (int i = 0; i < last_key_state_up.length; i++) {
//if(last_key_state_up[i] && iman.key_state_down[i]) 
//listener.keyPressed((KeyEvent) iman.lastKeyPressedEvents.get(i));
//if(last_key_state_down[i] && iman.key_state_up[i])
//listener.keyReleased((KeyEvent) iman.lastKeyReleasedEvents.get(i)); 
//} 
//last_key_state_down=cloneArray(iman.key_state_down);
//last_key_state_up = cloneArray(iman.key_state_up);
//}
//}
//private boolean[] cloneArray(boolean[] a) {
//if(a==null)
//return null;
//boolean [] r = new boolean[a.length];
//for (int i = 0; i < r.length; i++) 
//r[i]=a[i];
//return r;
//}
//public void stopChecking() {
//stoped=true;
//}
//@Override
//public void keyPressed(KeyEvent e) {}
//@Override
//public void keyReleased(KeyEvent e) { 
//}
////@Override
////public void keyTyped(KeyEvent e) {
////listener.keyTyped(e);
////} 
////} 
//}
//
///**
//* swing test
//*/
//static class KeyPressTest extends javax.swing.JFrame {
//public KeyPressTest() {
//super();
//initGUI();
//} 
//private void initGUI() {
//try {
//setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//new KeyListener2(this) { 
//@Override
//public void keyTyped(KeyEvent e) {} 
//@Override
//public void keyReleased(KeyEvent e) {
//System.out.println("keyReleased");
//} 
//@Override
//public void keyPressed(KeyEvent e) {
//System.out.println("keyPressed");
//}
//};
//pack();
//setSize(400, 300);
//} catch (Exception e) {
//e.printStackTrace();
//}
//}
//public static void main(String[] args) {
//SwingUtilities.invokeLater(new Runnable() {
//public void run() {
//KeyPressTest inst = new KeyPressTest();
//inst.setLocationRelativeTo(null);
//inst.setVisible(true);
//}
//});
//}
//}
//public static void main(String[] args) {
//	KeyPressTest.main(args);
//}
}