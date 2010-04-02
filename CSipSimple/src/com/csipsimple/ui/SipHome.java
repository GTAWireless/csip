/**
 * This file is part of CSipSimple.
 *
 *  CSipSimple is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  CSipSimple is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with CSipSimple.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.csipsimple.ui;

import com.csipsimple.service.SipService;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TabHost;
import com.csipsimple.R;

public class SipHome extends TabActivity {
	

    public static final int ACCOUNTS_MENU = Menu.FIRST + 1;
	public static final int PARAMS_MENU = Menu.FIRST + 2;
	public static final int CLOSE_MENU = Menu.FIRST + 3;
	private static final String THIS_FILE = "SIP HOME";
	
	
	private Intent serviceIntent;
	
	private Intent dialerIntent;
	private Intent csipIntent;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.d(THIS_FILE, "On Create SIPHOME");
        super.onCreate(savedInstanceState);
       
        if( ! getApplicationContext().getFileStreamPath(SipService.STACK_FILE_NAME).exists()){
			Intent welcomeIntent = new Intent(this, WelcomeScreen.class);
			welcomeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(welcomeIntent);
        }else{
        
	        Window w = getWindow();
	        w.setFormat(PixelFormat.OPAQUE);
	        w.setBackgroundDrawable((Drawable) null);
	        //Start the service
	        serviceIntent = new Intent(SipHome.this, SipService.class);
	        startService(serviceIntent);
        }

        final TabHost tabHost = getTabHost();
        Resources r = getResources();
        dialerIntent = new Intent(this, Dialer.class);
        csipIntent = new Intent(this, BuddyList.class);
        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("Dialer", r.getDrawable(R.drawable.ic_tab_selected_dialer))
                .setContent(dialerIntent));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Buddy list")
                .setContent(csipIntent));
        
    }
    
    @Override
    protected void onPause() {
    	Log.d(THIS_FILE, "On Pause SIPHOME");
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	Log.d(THIS_FILE, "On Resume SIPHOME");
    	super.onResume();
    }
    
    @Override
	protected void onDestroy() {
    	Log.d(THIS_FILE, "---DESTROY SIP HOME---");
		super.onDestroy();
		
		Log.d(THIS_FILE, "---DESTROY SIP HOME END---");
	}
    

    private void populateMenu(Menu menu) {
    	menu.add(Menu.NONE, ACCOUNTS_MENU, Menu.NONE, "Accounts").setIcon(
				R.drawable.ic_menu_accounts);
		menu.add(Menu.NONE, PARAMS_MENU, Menu.NONE, "Params").setIcon(
				android.R.drawable.ic_menu_preferences);
		menu.add(Menu.NONE, CLOSE_MENU, Menu.NONE, "Quit").setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);
	}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		populateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ACCOUNTS_MENU:
			startActivity(new Intent(this, AccountsList.class));
			return true;
		case PARAMS_MENU:
			startActivity(new Intent(this, Preferences.class));
			return true;
		case CLOSE_MENU:
	    	Log.d(THIS_FILE, "CLOSE");
	    	if(serviceIntent != null){
				stopService(serviceIntent);
			}
	    	serviceIntent = null;
			this.finish();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
}
