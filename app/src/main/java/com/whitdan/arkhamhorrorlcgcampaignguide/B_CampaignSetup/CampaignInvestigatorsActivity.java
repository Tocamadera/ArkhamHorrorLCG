package com.whitdan.arkhamhorrorlcgcampaignguide.B_CampaignSetup;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioInterludeActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioMainActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.E_EditMisc.ChooseSuppliesActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.CampaignEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamDbHelper;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/*
    CampaignInvestigatorsActivity - Allows the player to select which investigators they will be using and enter a
                                    name for the campaign, also includes a button to take you to chaos bag setup
 */

public class CampaignInvestigatorsActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    int investigators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(CampaignInvestigatorsActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_activity_campaign_investigators);
        globalVariables = (GlobalVariables) this.getApplication();

        // Resets a number of variables on creation
        investigators = 0;
        globalVariables.InvestigatorNames.clear();
        globalVariables.PlayerNames = new String[4];
        globalVariables.DeckNames = new String[4];
        globalVariables.DeckLists = new String[4];

        // Set campaign title
        TextView title = findViewById(R.id.campaign_name);
        switch (globalVariables.CurrentCampaign) {
            case 1:
                title.setText(R.string.night_campaign);
                break;
            case 2:
                title.setText(R.string.dunwich_campaign);
                break;
            case 3:
                title.setText(R.string.carcosa_campaign);
                break;
            case 4:
                title.setText(R.string.forgotten_campaign);
                break;
            case 5:
                title.setText(R.string.circle_campaign);
                break;
            case 6:
                title.setText(R.string.dream_campaign);
                break;
        }

        // Set fonts to relevant elements
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        Typeface wolgast = Typeface.createFromAsset(getAssets(), "fonts/wolgast.otf");
        title.setTypeface(teutonic);
        TextView select = findViewById(R.id.select_investigators);
        select.setTypeface(teutonic);
        TextView current = findViewById(R.id.current_team);
        current.setTypeface(teutonic);

        /*
            Setup checkboxes
          */
        final LinearLayout coreCheckboxes = findViewById(R.id.core_investigators);
        final LinearLayout dunwichCheckboxes = findViewById(R.id.dunwich_investigators);
        final LinearLayout carcosaCheckboxes = findViewById(R.id.carcosa_investigators);
        final LinearLayout forgottenCheckboxes = findViewById(R.id.forgotten_investigators);
        final LinearLayout circleCheckboxes = findViewById(R.id.circle_investigators);
        final LinearLayout dreamCheckboxes = findViewById(R.id.dream_investigators);
        final LinearLayout investigatorCheckboxes = findViewById(R.id.promo);
        LinearLayout normanCheckbox = findViewById(R.id.norman_promo);
        LinearLayout silasCheckbox = findViewById(R.id.silas_promo);

        // Setup buttons
        final ImageView nightButton = findViewById(R.id.notz);
        final ImageView dunwichButton = findViewById(R.id.dunwich);
        final ImageView carcosaButton = findViewById(R.id.carcosa);
        final ImageView forgottenButton = findViewById(R.id.forgotten);
        final ImageView circleButton = findViewById(R.id.circle);
        final ImageView dreamButton = findViewById(R.id.dream);
        final ImageView promo = findViewById(R.id.investigator);
        nightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightButton.setImageResource(R.drawable.notz_pressed);
                dunwichButton.setImageResource(R.drawable.dunwich);
                carcosaButton.setImageResource(R.drawable.carcosa);
                forgottenButton.setImageResource(R.drawable.forgotten);
                promo.setImageResource(R.drawable.investigator);
                coreCheckboxes.setVisibility(VISIBLE);
                dunwichCheckboxes.setVisibility(GONE);
                carcosaCheckboxes.setVisibility(GONE);
                forgottenCheckboxes.setVisibility(GONE);
                investigatorCheckboxes.setVisibility(GONE);
                circleButton.setImageResource(R.drawable.circle);
                circleCheckboxes.setVisibility(GONE);
                dreamButton.setImageResource(R.drawable.dream);
                dreamCheckboxes.setVisibility(GONE);
            }
        });
        dunwichButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightButton.setImageResource(R.drawable.notz);
                dunwichButton.setImageResource(R.drawable.dunwich_pressed);
                carcosaButton.setImageResource(R.drawable.carcosa);
                forgottenButton.setImageResource(R.drawable.forgotten);
                promo.setImageResource(R.drawable.investigator);
                coreCheckboxes.setVisibility(GONE);
                dunwichCheckboxes.setVisibility(VISIBLE);
                carcosaCheckboxes.setVisibility(GONE);
                forgottenCheckboxes.setVisibility(GONE);
                investigatorCheckboxes.setVisibility(GONE);
                circleButton.setImageResource(R.drawable.circle);
                circleCheckboxes.setVisibility(GONE);
                dreamButton.setImageResource(R.drawable.dream);
                dreamCheckboxes.setVisibility(GONE);
            }
        });
        carcosaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightButton.setImageResource(R.drawable.notz);
                dunwichButton.setImageResource(R.drawable.dunwich);
                carcosaButton.setImageResource(R.drawable.carcosa_pressed);
                forgottenButton.setImageResource(R.drawable.forgotten);
                promo.setImageResource(R.drawable.investigator);
                coreCheckboxes.setVisibility(GONE);
                dunwichCheckboxes.setVisibility(GONE);
                carcosaCheckboxes.setVisibility(VISIBLE);
                forgottenCheckboxes.setVisibility(GONE);
                investigatorCheckboxes.setVisibility(GONE);
                circleButton.setImageResource(R.drawable.circle);
                circleCheckboxes.setVisibility(GONE);
                dreamButton.setImageResource(R.drawable.dream);
                dreamCheckboxes.setVisibility(GONE);
            }
        });
        forgottenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightButton.setImageResource(R.drawable.notz);
                dunwichButton.setImageResource(R.drawable.dunwich);
                carcosaButton.setImageResource(R.drawable.carcosa);
                forgottenButton.setImageResource(R.drawable.forgotten_pressed);
                promo.setImageResource(R.drawable.investigator);
                coreCheckboxes.setVisibility(GONE);
                dunwichCheckboxes.setVisibility(GONE);
                carcosaCheckboxes.setVisibility(GONE);
                forgottenCheckboxes.setVisibility(VISIBLE);
                investigatorCheckboxes.setVisibility(GONE);
                circleButton.setImageResource(R.drawable.circle);
                circleCheckboxes.setVisibility(GONE);
                dreamButton.setImageResource(R.drawable.dream);
                dreamCheckboxes.setVisibility(GONE);
            }
        });
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightButton.setImageResource(R.drawable.notz);
                dunwichButton.setImageResource(R.drawable.dunwich);
                carcosaButton.setImageResource(R.drawable.carcosa);
                forgottenButton.setImageResource(R.drawable.forgotten);
                promo.setImageResource(R.drawable.investigator);
                coreCheckboxes.setVisibility(GONE);
                dunwichCheckboxes.setVisibility(GONE);
                carcosaCheckboxes.setVisibility(GONE);
                forgottenCheckboxes.setVisibility(GONE);
                investigatorCheckboxes.setVisibility(GONE);
                circleButton.setImageResource(R.drawable.circle_pressed);
                circleCheckboxes.setVisibility(VISIBLE);
                dreamButton.setImageResource(R.drawable.dream);
                dreamCheckboxes.setVisibility(GONE);
            }
        });
        dreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightButton.setImageResource(R.drawable.notz);
                dunwichButton.setImageResource(R.drawable.dunwich);
                carcosaButton.setImageResource(R.drawable.carcosa);
                forgottenButton.setImageResource(R.drawable.forgotten);
                promo.setImageResource(R.drawable.investigator);
                coreCheckboxes.setVisibility(GONE);
                dunwichCheckboxes.setVisibility(GONE);
                carcosaCheckboxes.setVisibility(GONE);
                forgottenCheckboxes.setVisibility(GONE);
                investigatorCheckboxes.setVisibility(GONE);
                circleButton.setImageResource(R.drawable.circle);
                circleCheckboxes.setVisibility(GONE);
                dreamButton.setImageResource(R.drawable.dream_pressed);
                dreamCheckboxes.setVisibility(VISIBLE);
            }
        });
        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightButton.setImageResource(R.drawable.notz);
                dunwichButton.setImageResource(R.drawable.dunwich);
                carcosaButton.setImageResource(R.drawable.carcosa);
                forgottenButton.setImageResource(R.drawable.forgotten);
                promo.setImageResource(R.drawable.investigator_pressed);
                coreCheckboxes.setVisibility(GONE);
                dunwichCheckboxes.setVisibility(GONE);
                carcosaCheckboxes.setVisibility(GONE);
                forgottenCheckboxes.setVisibility(GONE);
                investigatorCheckboxes.setVisibility(VISIBLE);
                circleButton.setImageResource(R.drawable.circle);
                circleCheckboxes.setVisibility(GONE);
                dreamButton.setImageResource(R.drawable.dream);
                dreamCheckboxes.setVisibility(GONE);
            }
        });

        // Hide investigators if expansion isn't owned
        String sharedPrefs = getResources().getString(R.string.shared_prefs);
        String dunwichOwnedString = getResources().getString(R.string.dunwich_setting);
        String carcosaOwnedString = getResources().getString(R.string.carcosa_setting);
        String forgottenOwnedString = getResources().getString(R.string.forgotten_setting);
        String circleOwnedString = getResources().getString(R.string.circle_setting);
        String dreamOwnedString = getResources().getString(R.string.dream_setting);
        String normanOwnedString = getResources().getString(R.string.norman_withers);
        String silasOwnedString = getResources().getString(R.string.silas_marsh);
        SharedPreferences settings = getSharedPreferences(sharedPrefs, 0);
        boolean dunwichOwned = settings.getBoolean(dunwichOwnedString, true);
        boolean carcosaOwned = settings.getBoolean(carcosaOwnedString, true);
        boolean forgottenOwned = settings.getBoolean(forgottenOwnedString, true);
        boolean circleOwned = settings.getBoolean(circleOwnedString, true);
        boolean dreamOwned = settings.getBoolean(dreamOwnedString, true);
        boolean normanOwned = settings.getBoolean(normanOwnedString, false);
        boolean silasOwned = settings.getBoolean(silasOwnedString, false);
        if (!dunwichOwned) {
            dunwichCheckboxes.setVisibility(GONE);
            dunwichButton.setVisibility(GONE);
        }
        if (!carcosaOwned) {
            carcosaCheckboxes.setVisibility(GONE);
            carcosaButton.setVisibility(GONE);
        }
        if (!forgottenOwned) {
            forgottenCheckboxes.setVisibility(GONE);
            forgottenButton.setVisibility(GONE);
        }
        if(!circleOwned) {
            circleCheckboxes.setVisibility(GONE);
            circleButton.setVisibility(GONE);
        }
        if(!dreamOwned) {
            dreamCheckboxes.setVisibility(GONE);
            dreamButton.setVisibility(GONE);
        }
        boolean promoCount = false;
        if (!normanOwned) {
            normanCheckbox.setVisibility(GONE);
            promoCount = true;
        }
        if (!silasOwned) {
            silasCheckbox.setVisibility(GONE);
            promoCount = true;
        }
        if(promoCount){
            promo.setVisibility(GONE);
        }
        // Set fonts and listeners to all checkboxes
        for (int i = 0; i < coreCheckboxes.getChildCount(); i++) {
            View view = coreCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorCheckboxListener());
            }
        }
        for (int i = 0; i < dunwichCheckboxes.getChildCount(); i++) {
            View view = dunwichCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorCheckboxListener());
            }
        }
        for (int i = 0; i < carcosaCheckboxes.getChildCount(); i++) {
            View view = carcosaCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorCheckboxListener());
            }
        }
        for (int i = 0; i < forgottenCheckboxes.getChildCount(); i++) {
            View view = forgottenCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorCheckboxListener());
            }
        }
        for (int i = 0; i < circleCheckboxes.getChildCount(); i++) {
            View view = circleCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorCheckboxListener());
            }
        }
        for (int i = 0; i < dreamCheckboxes.getChildCount(); i++) {
            View view = dreamCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorCheckboxListener());
            }
        }
        for (int i = 0; i < normanCheckbox.getChildCount(); i++) {
            View view = normanCheckbox.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorCheckboxListener());
            }
        }
        for (int i = 0; i < silasCheckbox.getChildCount(); i++) {
            View view = silasCheckbox.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
                ((CheckBox) view).setOnCheckedChangeListener(new InvestigatorCheckboxListener());
            }
        }

        // Setup edit text
        final EditText campaignName = findViewById(R.id.edit_name);
        campaignName.setTypeface(wolgast);
        setupUI(findViewById(R.id.parent_layout), this);

        // Chaos bag setup button
        Button chaosBag = findViewById(R.id.chaos_bag_button);
        chaosBag.setTypeface(teutonic);
        chaosBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CampaignInvestigatorsActivity.this, ChaosBagSetupActivity.class);
                startActivity(intent);
            }
        });

        // Back button
        Button backButton = findViewById(R.id.back_button);
        backButton.setTypeface(teutonic);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Continue button
        Button continueButton = findViewById(R.id.continue_button);
        continueButton.setTypeface(teutonic);
        if (globalVariables.CurrentCampaign == 4) {
            continueButton.setText(R.string.choose_supplies);
        }
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If no investigators are selected displays a toast
                if (globalVariables.InvestigatorNames.size() == 0) {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.must_investigator, Toast
                            .LENGTH_SHORT);
                    toast.show();
                }
                // If no campaign name is entered displays a toast
                else if (campaignName.length() == 0) {
                    Toast toast = Toast.makeText(getBaseContext(), R.string.must_campaign_name, Toast
                            .LENGTH_SHORT);
                    toast.show();
                }
                // If an investigator has been selected and a campaign name enters, opens a dialog to confirm
                // starting the campaign
                else {
                    DialogFragment startCampaign = new StartCampaignDialog();
                    Bundle bundle = new Bundle();
                    String campaign = campaignName.getText().toString().trim();
                    bundle.putString("campaign", campaign);
                    startCampaign.setArguments(bundle);
                    startCampaign.show(getFragmentManager(), "campaign");
                }
            }
        });
    }


    // OnCheckedChangeListener for all of the investigator checkboxes
    private class InvestigatorCheckboxListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            View parent = (View) buttonView.getParent().getParent().getParent().getParent().getParent();
            int removeInvestigator = -1;

            /*
                For each checkbox:
                    if it is checked and there are less than 4 investigators add the investigator to the
                    InvestigatorNames array and increment the number of investigators,
                    if it is checked and there are 4 investigators, uncheck the box,
                    if it is unchecked, decrement the number of investigators and remove the investigator from the
                    InvestigatorNames array
             */
            if (buttonView.isPressed()) {
                switch (buttonView.getId()) {
                    case R.id.roland_banks:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.ROLAND_BANKS);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.ROLAND_BANKS) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.skids_otoole:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.SKIDS_OTOOLE);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.SKIDS_OTOOLE) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.agnes_baker:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.AGNES_BAKER);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.AGNES_BAKER) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.daisy_walker:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.DAISY_WALKER);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.DAISY_WALKER) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.wendy_adams:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.WENDY_ADAMS);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.WENDY_ADAMS) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.zoey_samaras:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.ZOEY_SAMARAS);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.ZOEY_SAMARAS) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.rex_murphy:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.REX_MURPHY);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.REX_MURPHY) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.jenny_barnes:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.JENNY_BARNES);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.JENNY_BARNES) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.jim_culver:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.JIM_CULVER);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.JIM_CULVER) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.ashcan_pete:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.ASHCAN_PETE);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.ASHCAN_PETE) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.mark_harrigan:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.MARK_HARRIGAN);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.MARK_HARRIGAN) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.minh_thi_phan:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.MINH_THI_PHAN);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.MINH_THI_PHAN) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.sefina_rousseau:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.SEFINA_ROUSSEAU);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.SEFINA_ROUSSEAU) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.akachi_onyele:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.AKACHI_ONYELE);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.AKACHI_ONYELE) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.william_yorick:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.WILLIAM_YORICK);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.WILLIAM_YORICK) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.lola_hayes:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.LOLA_HAYES);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.LOLA_HAYES) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.leo_anderson:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.LEO_ANDERSON);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.LEO_ANDERSON) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.ursula_downs:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.URSULA_DOWNS);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.URSULA_DOWNS) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.finn_edwards:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.FINN_EDWARDS);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.FINN_EDWARDS) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.father_mateo:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.FATHER_MATEO);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.FATHER_MATEO) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.calvin_wright:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.CALVIN_WRIGHT);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.CALVIN_WRIGHT) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.marie_lambeau:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.MARIE_LAMBEAU);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.MARIE_LAMBEAU) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.norman_withers:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.NORMAN_WITHERS);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.NORMAN_WITHERS) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.carolyn_fern:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.CAROLYN_FERN);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.CAROLYN_FERN) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.silas_marsh:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.SILAS_MARSH);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.SILAS_MARSH) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.joe_diamond:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.JOE_DIAMOND);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.JOE_DIAMOND) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.preston_fairmont:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.PRESTON_FAIRMONT);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.PRESTON_FAIRMONT) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.diana_stanley:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.DIANA_STANLEY);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.DIANA_STANLEY) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.rita_young:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.RITA_YOUNG);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.RITA_YOUNG) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.tommy_muldoon:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.TOMMY_MULDOON);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.TOMMY_MULDOON) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.mandy_thompson:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.MANDY_THOMPSON);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.MANDY_THOMPSON) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.tony_morgan:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.TONY_MORGAN);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.TONY_MORGAN) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.luke_robinson:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.LUKE_ROBINSON);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.LUKE_ROBINSON) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                    case R.id.patrice_hathaway:
                        if (isChecked && investigators < 4) {
                            globalVariables.InvestigatorNames.add(Investigator.PATRICE_HATHAWAY);
                            investigators++;
                        } else if (isChecked) {
                            buttonView.setChecked(false);
                        } else {
                            investigators--;
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == Investigator.PATRICE_HATHAWAY) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            }
                        }
                        break;
                }
            }

            /*
                Show the right views for the number of investigators and set the right font to the name
             */
            LinearLayout investigatorOne = parent.findViewById(R.id.investigator_one);
            LinearLayout investigatorTwo = parent.findViewById(R.id.investigator_two);
            LinearLayout investigatorThree = parent.findViewById(R.id.investigator_three);
            LinearLayout investigatorFour = parent.findViewById(R.id.investigator_four);
            TextView investigatorOneName = parent.findViewById(R.id.investigator_one_name);
            TextView investigatorTwoName = parent.findViewById(R.id.investigator_two_name);
            TextView investigatorThreeName = parent.findViewById(R.id.investigator_three_name);
            TextView investigatorFourName = parent.findViewById(R.id.investigator_four_name);
            TextView investigatorOneLink = findViewById(R.id.investigator_one_link);
            TextView investigatorTwoLink = findViewById(R.id.investigator_two_link);
            TextView investigatorThreeLink = findViewById(R.id.investigator_three_link);
            TextView investigatorFourLink = findViewById(R.id.investigator_four_link);
            String[] investigatorNames = getResources().getStringArray(R.array.investigators);
            Typeface arnoprobold = Typeface.createFromAsset(getAssets(), "fonts/arnoprobold.otf");
            Typeface wolgast = Typeface.createFromAsset(getAssets(), "fonts/wolgast.otf");
            // For each investigator, set it visible or not, apply the right name to it, set the right typeface and
            // set a listener to the link
            if (investigators > 0) {
                investigatorOne.setVisibility(VISIBLE);
                String nameOne = investigatorNames[globalVariables.InvestigatorNames.get(0)];
                investigatorOneName.setText(nameOne);
                investigatorOneName.setTypeface(arnoprobold);
                investigatorOneLink.setTypeface(wolgast);
                investigatorOneLink.setPaintFlags(investigatorOneLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                investigatorOneLink.setOnClickListener(new investigatorLinkListener());
            } else {
                investigatorOne.setVisibility(GONE);
            }

            if (investigators > 1) {
                investigatorTwo.setVisibility(VISIBLE);
                String nameTwo = investigatorNames[globalVariables.InvestigatorNames.get(1)];
                investigatorTwoName.setText(nameTwo);
                investigatorTwoName.setTypeface(arnoprobold);
                investigatorTwoLink.setTypeface(wolgast);
                investigatorTwoLink.setPaintFlags(investigatorTwoLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                investigatorTwoLink.setOnClickListener(new investigatorLinkListener());
            } else {
                investigatorTwo.setVisibility(GONE);
            }

            if (investigators > 2) {
                investigatorThree.setVisibility(VISIBLE);
                String nameThree = investigatorNames[globalVariables.InvestigatorNames.get(2)];
                investigatorThreeName.setText(nameThree);
                investigatorThreeName.setTypeface(arnoprobold);
                investigatorThreeLink.setTypeface(wolgast);
                investigatorThreeLink.setPaintFlags(investigatorThreeLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                investigatorThreeLink.setOnClickListener(new investigatorLinkListener());
            } else {
                investigatorThree.setVisibility(GONE);
            }

            if (investigators > 3) {
                investigatorFour.setVisibility(VISIBLE);
                String nameFour = investigatorNames[globalVariables.InvestigatorNames.get(3)];
                investigatorFourName.setText(nameFour);
                investigatorFourName.setTypeface(arnoprobold);
                investigatorFourLink.setTypeface(wolgast);
                investigatorFourLink.setPaintFlags(investigatorFourLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                investigatorFourLink.setOnClickListener(new investigatorLinkListener());
            } else {
                investigatorFour.setVisibility(GONE);
            }

            // Refactor player names, deck names and decklists
            if (removeInvestigator > -1) {
                switch (removeInvestigator) {
                    case 0:
                        globalVariables.PlayerNames[0] = globalVariables.PlayerNames[1];
                        globalVariables.DeckNames[0] = globalVariables.DeckNames[1];
                        globalVariables.DeckLists[0] = globalVariables.DeckLists[1];
                    case 1:
                        globalVariables.PlayerNames[1] = globalVariables.PlayerNames[2];
                        globalVariables.DeckNames[1] = globalVariables.DeckNames[2];
                        globalVariables.DeckLists[1] = globalVariables.DeckLists[2];
                    case 2:
                        globalVariables.PlayerNames[2] = globalVariables.PlayerNames[3];
                        globalVariables.DeckNames[2] = globalVariables.DeckNames[3];
                        globalVariables.DeckLists[2] = globalVariables.DeckLists[3];
                    case 3:
                        globalVariables.PlayerNames[3] = null;
                        globalVariables.DeckNames[3] = null;
                        globalVariables.DeckLists[3] = null;
                }
                // Reset the link text with the right name or nothing
                if (globalVariables.PlayerNames[0] != null) {
                    if (globalVariables.PlayerNames[0].length() > 0) {
                        String name = globalVariables.PlayerNames[0] + " ";
                        investigatorOneLink.setText(name);
                    } else {
                        investigatorOneLink.setText(R.string.edit_info);
                    }
                } else {
                    investigatorOneLink.setText(R.string.edit_info);
                }

                if (globalVariables.PlayerNames[1] != null) {
                    if (globalVariables.PlayerNames[1].length() > 0) {
                        String name = globalVariables.PlayerNames[1] + " ";
                        investigatorTwoLink.setText(name);
                    } else {
                        investigatorTwoLink.setText(R.string.edit_info);
                    }
                } else {
                    investigatorTwoLink.setText(R.string.edit_info);
                }

                if (globalVariables.PlayerNames[2] != null) {
                    if (globalVariables.PlayerNames[2].length() > 0) {
                        String name = globalVariables.PlayerNames[2] + " ";
                        investigatorThreeLink.setText(name);
                    } else {
                        investigatorThreeLink.setText(R.string.edit_info);
                    }
                } else {
                    investigatorThreeLink.setText(R.string.edit_info);
                }

                if (globalVariables.PlayerNames[3] != null) {
                    if (globalVariables.PlayerNames[3].length() > 0) {
                        String name = globalVariables.PlayerNames[3] + " ";
                        investigatorFourLink.setText(name);
                    } else {
                        investigatorFourLink.setText(R.string.edit_info);
                    }
                } else {
                    investigatorFourLink.setText(R.string.edit_info);
                }

            }
        }
    }


    // Opens a dialog to enter all of the relevant information
    private class investigatorLinkListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            DialogFragment investigatorInfoFragment = new investigatorInfoFragment();
            // Pass the investigator number to the dialog in a bundle
            Bundle bundle = new Bundle();
            int investigator = -1;
            switch (view.getId()) {
                case R.id.investigator_one_link:
                    investigator = 0;
                    break;
                case R.id.investigator_two_link:
                    investigator = 1;
                    break;
                case R.id.investigator_three_link:
                    investigator = 2;
                    break;
                case R.id.investigator_four_link:
                    investigator = 3;
                    break;
            }
            bundle.putInt("investigator", investigator);
            investigatorInfoFragment.setArguments(bundle);
            investigatorInfoFragment.show(getFragmentManager(), "investigator");
        }
    }

    // Dialog fragment for entering information about each investigator
    public static class investigatorInfoFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get investigator number from the bundle
            Bundle bundle = this.getArguments();
            final int investigator = bundle.getInt("investigator");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.b_dialog_campaign_investigators, null);
            setupUI(v, getActivity());

            // Get the name of the investigator
            String[] investigatorNames = getResources().getStringArray(R.array.investigators);
            String name = investigatorNames[globalVariables.InvestigatorNames.get(investigator)];

            // Get the relevant views and set fonts
            TextView nameText = v.findViewById(R.id.investigator_name);
            nameText.setText(name);
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            nameText.setTypeface(arnoprobold);
            Typeface wolgast = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wolgast.otf");
            final EditText playerName = v.findViewById(R.id.edit_player_name);
            final EditText playerDeck = v.findViewById(R.id.edit_player_deck);
            final EditText playerLink = v.findViewById(R.id.edit_player_link);
            playerName.setTypeface(wolgast);
            playerDeck.setTypeface(wolgast);
            playerLink.setTypeface(wolgast);
            playerName.setText(globalVariables.PlayerNames[investigator]);
            playerDeck.setText(globalVariables.DeckNames[investigator]);
            playerLink.setText(globalVariables.DeckLists[investigator]);
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);

            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Saves the information the player has entered to the relevant variables
                    globalVariables.PlayerNames[investigator] = playerName.getText().toString().trim();
                    globalVariables.DeckNames[investigator] = playerDeck.getText().toString().trim();
                    globalVariables.DeckLists[investigator] = playerLink.getText().toString().trim();

                    // Sets the player's name to the relevant textview in the core activity
                    TextView investigatorLink = getActivity().findViewById(R.id.investigator_one_link);
                    switch (investigator) {
                        case 0:
                            investigatorLink = getActivity().findViewById(R.id.investigator_one_link);
                            break;
                        case 1:
                            investigatorLink = getActivity().findViewById(R.id.investigator_two_link);
                            break;
                        case 2:
                            investigatorLink = getActivity().findViewById(R.id.investigator_three_link);
                            break;
                        case 3:
                            investigatorLink = getActivity().findViewById(R.id.investigator_four_link);
                            break;
                    }
                    if (globalVariables.PlayerNames[investigator].length() > 0) {
                        String name = globalVariables.PlayerNames[investigator] + " ";
                        investigatorLink.setText(name);
                    } else {
                        investigatorLink.setText(R.string.edit_info);
                    }

                    dismiss();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            builder.setView(v);
            return builder.create();
        }
    }


    // Dialog box for confirming the start of a campaign
    public static class StartCampaignDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the campaign name from the bundle
            Bundle bundle = this.getArguments();
            final String campaign = bundle.getString("campaign");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater and inflate the view
            View v = View.inflate(getActivity(), R.layout.b_dialog_start_campaign, null);

            // Find views and set fonts
            Typeface arnoprobold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnoprobold.otf");
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Typeface arnopro = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arnopro.otf");
            Typeface wolgast = Typeface.createFromAsset(getActivity().getAssets(), "fonts/wolgast.otf");
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            TextView confirm = v.findViewById(R.id.confirm_start_campaign);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);
            confirm.setTypeface(arnoprobold);

            // Change confirm text if on Forgotten Age
            if(globalVariables.CurrentCampaign == 4){
                confirm.setText(R.string.continue_question);
            }

            // Set campaign title
            TextView campaignName = v.findViewById(R.id.campaign_name);
            campaignName.setTypeface(wolgast);
            campaignName.setText(campaign);

            /*
                Show the right views for the number of investigators and set the right font to the name
             */
            LinearLayout investigatorOne = v.findViewById(R.id.investigator_one);
            LinearLayout investigatorTwo = v.findViewById(R.id.investigator_two);
            LinearLayout investigatorThree = v.findViewById(R.id.investigator_three);
            LinearLayout investigatorFour = v.findViewById(R.id.investigator_four);
            TextView investigatorOneName = v.findViewById(R.id.investigator_one_name);
            TextView investigatorTwoName = v.findViewById(R.id.investigator_two_name);
            TextView investigatorThreeName = v.findViewById(R.id.investigator_three_name);
            TextView investigatorFourName = v.findViewById(R.id.investigator_four_name);
            TextView investigatorOneLink = v.findViewById(R.id.investigator_one_player);
            TextView investigatorTwoLink = v.findViewById(R.id.investigator_two_player);
            TextView investigatorThreeLink = v.findViewById(R.id.investigator_three_player);
            TextView investigatorFourLink = v.findViewById(R.id.investigator_four_player);
            String[] investigatorNames = getResources().getStringArray(R.array.investigators);
            // For each investigator, set it visible or not, apply the right name to it, set the right typeface and
            // set a listener to the link
            if (globalVariables.InvestigatorNames.size() > 0) {
                investigatorOne.setVisibility(VISIBLE);
                String nameOne = investigatorNames[globalVariables.InvestigatorNames.get(0)];
                String playerOne = globalVariables.PlayerNames[0];
                investigatorOneName.setText(nameOne);
                investigatorOneName.setTypeface(arnoprobold);
                investigatorOneLink.setTypeface(arnopro);
                investigatorOneLink.setText(playerOne);
            } else {
                investigatorOne.setVisibility(GONE);
            }

            if (globalVariables.InvestigatorNames.size() > 1) {
                investigatorTwo.setVisibility(VISIBLE);
                String nameTwo = investigatorNames[globalVariables.InvestigatorNames.get(1)];
                String playerTwo = globalVariables.PlayerNames[1];
                investigatorTwoName.setText(nameTwo);
                investigatorTwoName.setTypeface(arnoprobold);
                investigatorTwoLink.setTypeface(arnopro);
                investigatorTwoLink.setText(playerTwo);
            } else {
                investigatorTwo.setVisibility(GONE);
            }

            if (globalVariables.InvestigatorNames.size() > 2) {
                investigatorThree.setVisibility(VISIBLE);
                String nameThree = investigatorNames[globalVariables.InvestigatorNames.get(2)];
                String playerThree = globalVariables.PlayerNames[2];
                investigatorThreeName.setText(nameThree);
                investigatorThreeName.setTypeface(arnoprobold);
                investigatorThreeLink.setTypeface(arnopro);
                investigatorThreeLink.setText(playerThree);
            } else {
                investigatorThree.setVisibility(GONE);
            }

            if (globalVariables.InvestigatorNames.size() > 3) {
                investigatorFour.setVisibility(VISIBLE);
                String nameFour = investigatorNames[globalVariables.InvestigatorNames.get(3)];
                String playerFour = globalVariables.PlayerNames[3];
                investigatorFourName.setText(nameFour);
                investigatorFourName.setTypeface(arnoprobold);
                investigatorFourLink.setTypeface(arnopro);
                investigatorFourLink.setText(playerFour);
            } else {
                investigatorFour.setVisibility(GONE);
            }

            /*
                Show options for which scenario to start with for the Dunwich Legacy campaign
             */
            if (globalVariables.CurrentCampaign == 2) {
                globalVariables.FirstScenario = 0;
                RadioGroup options = v.findViewById(R.id.start_campaign_options);
                RadioButton optionOne = v.findViewById(R.id.start_campaign_option_one);
                RadioButton optionTwo = v.findViewById(R.id.start_campaign_option_two);
                options.setVisibility(VISIBLE);
                optionOne.setText(R.string.dunwich_start_option_one);
                optionOne.setTypeface(arnopro);
                optionTwo.setText(R.string.dunwich_start_option_two);
                optionTwo.setTypeface(arnopro);
                options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        switch (i) {
                            case R.id.start_campaign_option_one:
                                globalVariables.FirstScenario = 1;
                                break;
                            case R.id.start_campaign_option_two:
                                globalVariables.FirstScenario = 2;
                                break;
                        }
                    }
                });
            }


            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // If on Dunwich and no option has been selected, show a toast
                    if (globalVariables.CurrentCampaign == 2 && globalVariables.FirstScenario == 0) {
                        Toast toast = Toast.makeText(getActivity(), "You must select an option.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    // Save and start the campaign
                    else {
                        // Clear and then set investigators
                        globalVariables.Investigators.clear();
                        globalVariables.SavedInvestigators.clear();
                        boolean lola = false;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            globalVariables.Investigators.add(new Investigator(globalVariables.InvestigatorNames.get(i),
                                    globalVariables.PlayerNames[i], globalVariables.DeckNames[i], globalVariables
                                    .DeckLists[i]));
                            globalVariables.InvestigatorsInUse[globalVariables.InvestigatorNames.get(i)] = 1;

                            // Check if Lola Hayes is in use for Carcosa prologue
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.LOLA_HAYES) {
                                lola = true;
                            }
                        }

                        // Clear variables which have been used
                        globalVariables.InvestigatorNames.clear();
                        globalVariables.PlayerNames = new String[4];
                        globalVariables.DeckLists = new String[4];

                        // If on Forgotten Age, go to supplies screen
                        if (globalVariables.CurrentCampaign == 4) {
                            globalVariables.CampaignName = campaign;
                            Intent intent = new Intent(getActivity(), ChooseSuppliesActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            dismiss();
                        } else {
                            // Set current scenario to first scenario
                            if (globalVariables.CurrentCampaign == 2) {
                                globalVariables.CurrentScenario = globalVariables.FirstScenario;
                            } else if (globalVariables.CurrentCampaign == 3 && lola) {
                                globalVariables.CurrentScenario = 0;
                            } else {
                                globalVariables.CurrentScenario = 1;
                            }

                            // Save the new campaign
                            newCampaign(getActivity(), campaign);

                            // Go to scenario setup
                            Intent intent = new Intent(getActivity(), ScenarioMainActivity
                                    .class);
                            if (globalVariables.CurrentScenario == 0) {
                                intent = new Intent(getActivity(), ScenarioInterludeActivity.class);
                            }
                            startActivity(intent);
                            dismiss();
                        }
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            builder.setView(v);
            return builder.create();
        }

    }


    /*
     Saves a new campaign to the database (called from startScenario - included separately for neatness)
      */
    public static void newCampaign(Context context, String campaignName) {

        // Get a writable database
        ArkhamDbHelper dbHelper = new ArkhamDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create entry in campaigns table
        ContentValues campaignValues = new ContentValues();
        campaignValues.put(CampaignEntry.COLUMN_CAMPAIGN_VERSION, 2);
        campaignValues.put(ArkhamContract.CampaignEntry.COLUMN_CAMPAIGN_NAME, campaignName);
        campaignValues.put(CampaignEntry.COLUMN_CHAOS_BAG, -1);
        campaignValues.put(CampaignEntry.COLUMN_CURRENT_CAMPAIGN, globalVariables.CurrentCampaign);
        campaignValues.put(CampaignEntry.COLUMN_CURRENT_SCENARIO, globalVariables.CurrentScenario);
        campaignValues.put(CampaignEntry.COLUMN_DIFFICULTY, globalVariables.CurrentDifficulty);
        campaignValues.put(CampaignEntry.COLUMN_ROLAND_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .ROLAND_BANKS]);
        campaignValues.put(CampaignEntry.COLUMN_DAISY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .DAISY_WALKER]);
        campaignValues.put(CampaignEntry.COLUMN_SKIDS_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .SKIDS_OTOOLE]);
        campaignValues.put(CampaignEntry.COLUMN_AGNES_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .AGNES_BAKER]);
        campaignValues.put(CampaignEntry.COLUMN_WENDY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .WENDY_ADAMS]);
        campaignValues.put(CampaignEntry.COLUMN_ZOEY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .ZOEY_SAMARAS]);
        campaignValues.put(CampaignEntry.COLUMN_REX_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .REX_MURPHY]);
        campaignValues.put(CampaignEntry.COLUMN_JENNY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .JENNY_BARNES]);
        campaignValues.put(CampaignEntry.COLUMN_JIM_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .JIM_CULVER]);
        campaignValues.put(CampaignEntry.COLUMN_PETE_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .ASHCAN_PETE]);
        campaignValues.put(CampaignEntry.COLUMN_MARK_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .MARK_HARRIGAN]);
        campaignValues.put(CampaignEntry.COLUMN_MINH_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .MINH_THI_PHAN]);
        campaignValues.put(CampaignEntry.COLUMN_AKACHI_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .AKACHI_ONYELE]);
        campaignValues.put(CampaignEntry.COLUMN_SEFINA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .SEFINA_ROUSSEAU]);
        campaignValues.put(CampaignEntry.COLUMN_WILLIAM_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .WILLIAM_YORICK]);
        campaignValues.put(CampaignEntry.COLUMN_LOLA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .LOLA_HAYES]);
        campaignValues.put(CampaignEntry.COLUMN_MARIE_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .MARIE_LAMBEAU]);
        campaignValues.put(CampaignEntry.COLUMN_NORMAN_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .NORMAN_WITHERS]);
        campaignValues.put(CampaignEntry.COLUMN_CAROLYN_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .CAROLYN_FERN]);
        campaignValues.put(CampaignEntry.COLUMN_SILAS_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .SILAS_MARSH]);
        campaignValues.put(CampaignEntry.COLUMN_LEO_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .LEO_ANDERSON]);
        campaignValues.put(CampaignEntry.COLUMN_URSULA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .URSULA_DOWNS]);
        campaignValues.put(CampaignEntry.COLUMN_FINN_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .FINN_EDWARDS]);
        campaignValues.put(CampaignEntry.COLUMN_MATEO_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .FATHER_MATEO]);
        campaignValues.put(CampaignEntry.COLUMN_CALVIN_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .CALVIN_WRIGHT]);
        campaignValues.put(CampaignEntry.COLUMN_JOE_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .JOE_DIAMOND]);
        campaignValues.put(CampaignEntry.COLUMN_PRESTON_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .PRESTON_FAIRMONT]);
        campaignValues.put(CampaignEntry.COLUMN_DIANA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .DIANA_STANLEY]);
        campaignValues.put(CampaignEntry.COLUMN_RITA_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .RITA_YOUNG]);
        campaignValues.put(CampaignEntry.COLUMN_TOMMY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .TOMMY_MULDOON]);
        campaignValues.put(CampaignEntry.COLUMN_MANDY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .MANDY_THOMPSON]);
        campaignValues.put(CampaignEntry.COLUMN_TONY_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .TONY_MORGAN]);
        campaignValues.put(CampaignEntry.COLUMN_LUKE_INUSE, globalVariables.InvestigatorsInUse[Investigator
                .LUKE_ROBINSON]);
        campaignValues.put(CampaignEntry.COLUMN_PATRICE_HATHAWAY, globalVariables.InvestigatorsInUse[Investigator
                .PATRICE_HATHAWAY]);
        long newCampaignId = db.insert(CampaignEntry.TABLE_NAME, null, campaignValues);
        globalVariables.CampaignID = newCampaignId;

        // Create campaign specific table
        int currentCampaign = globalVariables.CurrentCampaign;
        switch (currentCampaign) {
            // Night of the Zealot
            case 1:
                ContentValues nightValues = new ContentValues();
                nightValues.put(ArkhamContract.NightEntry.PARENT_ID, newCampaignId);
                db.insert(ArkhamContract.NightEntry.TABLE_NAME, null, nightValues);
                break;
            // The Dunwich Legacy
            case 2:
                ContentValues dunwichValues = new ContentValues();
                dunwichValues.put(ArkhamContract.DunwichEntry.PARENT_ID, newCampaignId);
                dunwichValues.put(ArkhamContract.DunwichEntry.COLUMN_FIRST_SCENARIO,
                        globalVariables.FirstScenario);
                db.insert(ArkhamContract.DunwichEntry.TABLE_NAME, null, dunwichValues);
                break;
            // Path to Carcosa
            case 3:
                ContentValues carcosaValues = new ContentValues();
                carcosaValues.put(ArkhamContract.CarcosaEntry.PARENT_ID, newCampaignId);
                carcosaValues.put(ArkhamContract.CarcosaEntry.COLUMN_DREAMS, 0);
                db.insert(ArkhamContract.CarcosaEntry.TABLE_NAME, null, carcosaValues);
                break;
            // The Forgotten Age
            case 4:
                ContentValues forgottenValues = new ContentValues();
                forgottenValues.put(ArkhamContract.ForgottenEntry.PARENT_ID, newCampaignId);
                db.insert(ArkhamContract.ForgottenEntry.TABLE_NAME, null, forgottenValues);
                break;
        }

        // Create entries for every investigator in the investigators table
        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
            ContentValues investigatorValues = new ContentValues();
            investigatorValues.put(ArkhamContract.InvestigatorEntry.PARENT_ID, newCampaignId);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.INVESTIGATOR_ID, i);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_NAME, globalVariables
                    .Investigators.get(i).Name);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_STATUS, 1);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE, 0);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR, 0);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_TOTAL_XP, 0);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_AVAILABLE_XP, 0);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_SPENT_XP, 0);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_PLAYER, globalVariables
                    .Investigators.get(i).PlayerName);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DECKNAME, globalVariables
                    .Investigators.get(i).DeckName);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_DECKLIST, globalVariables
                    .Investigators.get(i).Decklist);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_PROVISIONS, globalVariables
                    .Investigators.get(i).Provisions);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_MEDICINE, globalVariables
                    .Investigators.get(i).Medicine);
            investigatorValues.put(ArkhamContract.InvestigatorEntry.COLUMN_INVESTIGATOR_SUPPLIES, globalVariables
                    .Investigators.get(i).Supplies);
            db.insert(ArkhamContract.InvestigatorEntry.TABLE_NAME, null, investigatorValues);
        }
    }


    // Hides the soft keyboard when someone clicks outside the EditText
    public static void setupUI(final View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager =
                            (InputMethodManager) activity.getSystemService(
                                    Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }
}
