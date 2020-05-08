package com.whitdan.arkhamhorrorlcgcampaignguide.E_EditMisc;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
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
import android.widget.TextView;
import android.widget.Toast;

import com.whitdan.arkhamhorrorlcgcampaignguide.A_Menus.MainMenuActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioMainActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.C_Scenario.ScenarioResolutionActivity;
import com.whitdan.arkhamhorrorlcgcampaignguide.R;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.GlobalVariables;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.Investigator;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.whitdan.arkhamhorrorlcgcampaignguide.R.array.investigators;

public class EditTeamActivity extends AppCompatActivity {

    static GlobalVariables globalVariables;
    private static int investigatorsCount;
    public static Investigator investigatorOne;
    public static Investigator investigatorTwo;
    public static Investigator investigatorThree;
    public static Investigator investigatorFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // If app is reopening after the process is killed, kick back to the main menu (stops the activity from
        // showing up unpopulated)
        if (savedInstanceState != null) {
            Intent intent = new Intent(EditTeamActivity.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.e_activity_edit_team);
        globalVariables = (GlobalVariables) this.getApplication();

        // Get array of the names of all of the game's investigators
        String[] investigatorNames = getResources().getStringArray(investigators);

        investigatorsCount = 0;
        investigatorOne = null;
        investigatorTwo = null;
        investigatorThree = null;
        investigatorFour = null;
        globalVariables.InvestigatorNames.clear();
        globalVariables.PlayerNames = new String[4];
        globalVariables.DeckNames = new String[4];
        globalVariables.DeckLists = new String[4];

        // Set fonts
        Typeface teutonic = Typeface.createFromAsset(getAssets(), "fonts/teutonic.ttf");
        Typeface arnopro = Typeface.createFromAsset(getAssets(), "fonts/arnopro.otf");
        TextView saved = findViewById(R.id.saved_investigators);
        TextView select = findViewById(R.id.select_investigators);
        TextView current = findViewById(R.id.current_team);
        saved.setTypeface(teutonic);
        select.setTypeface(teutonic);
        current.setTypeface(teutonic);

        // Set title
        TextView title = findViewById(R.id.current_scenario_name);
        title.setTypeface(teutonic);
        globalVariables.setTitle(title);

        // Setup checkboxes for saved investigators
        LinearLayout savedInvestigators = findViewById(R.id.saved_investigators_layout);
        for (int i = 0; i < globalVariables.Investigators.size(); i++) {
            // Check that the investigator isn't dead
            if (globalVariables.Investigators.get(i).Status == 1) {
                CheckBox investigator = new CheckBox(this);
                // Set attributes to the checkbox
                investigator.setTypeface(arnopro);
                investigator.setButtonDrawable(R.drawable.checkbox_style);
                investigator.setPadding(0, getResources().getDimensionPixelSize(R.dimen.checkbox_padding),
                        getResources().getDimensionPixelSize(R.dimen.checkbox_padding), 0);
                investigator.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen
                        .arnopro_textsize));
                investigator.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
                String investigatorName;
                // Set the investigator name (if there's a player name add it in brackets to the end)
                if (globalVariables.Investigators.get(i).PlayerName != null && globalVariables.Investigators.get(i)
                        .PlayerName.length() > 0) {
                    investigatorName = investigatorNames[globalVariables.Investigators.get(i).Name] + " (" +
                            globalVariables.Investigators.get(i).PlayerName + ")";
                } else {
                    investigatorName = investigatorNames[globalVariables.Investigators.get(i).Name];
                }
                investigator.setText(investigatorName);
                // Set the Id of the checkbox to the number of the investigator in the array
                investigator.setId(i);
                investigator.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
                savedInvestigators.addView(investigator);
                investigator.setChecked(true);
            }
        }
        for (int i = 0; i < globalVariables.SavedInvestigators.size(); i++) {
            CheckBox investigator = new CheckBox(this);
            // Set attributes to the checkbox
            investigator.setTypeface(arnopro);
            investigator.setButtonDrawable(R.drawable.checkbox_style);
            investigator.setPadding(0, getResources().getDimensionPixelSize(R.dimen.checkbox_padding),
                    getResources().getDimensionPixelSize(R.dimen.checkbox_padding), 0);
            investigator.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.arnopro_textsize));
            investigator.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
            String investigatorName;
            // Set the investigator name (if there's a player name add it in brackets to the end)
            if (globalVariables.SavedInvestigators.get(i).PlayerName != null && globalVariables.SavedInvestigators
                    .get(i).PlayerName.length() > 0) {
                investigatorName = investigatorNames[globalVariables.SavedInvestigators.get(i).Name] + " (" +
                        globalVariables.SavedInvestigators.get(i).PlayerName + ")";
            } else {
                investigatorName = investigatorNames[globalVariables.SavedInvestigators.get(i).Name];
            }
            investigator.setText(investigatorName);
            // Set the Id of the checkbox to the number of the investigator in the array + 100
            investigator.setId(i + 100);
            investigator.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            savedInvestigators.addView(investigator);
            investigator.setChecked(false);
        }
        if (savedInvestigators.getChildCount() == 0) {
            saved.setVisibility(GONE);
        }

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

        for (int i = 0; i < coreCheckboxes.getChildCount(); i++) {
            View view = coreCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
            }
        }
        for (int i = 0; i < dunwichCheckboxes.getChildCount(); i++) {
            View view = dunwichCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
            }
        }
        for (int i = 0; i < carcosaCheckboxes.getChildCount(); i++) {
            View view = carcosaCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
            }
        }
        for (int i = 0; i < forgottenCheckboxes.getChildCount(); i++) {
            View view = forgottenCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
            }
        }
        for (int i = 0; i < circleCheckboxes.getChildCount(); i++) {
            View view = circleCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
            }
        }
        for (int i = 0; i < dreamCheckboxes.getChildCount(); i++) {
            View view = dreamCheckboxes.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
            }
        }
        for (int i = 0; i < normanCheckbox.getChildCount(); i++) {
            View view = normanCheckbox.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
            }
        }
        for (int i = 0; i < silasCheckbox.getChildCount(); i++) {
            View view = silasCheckbox.getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setTypeface(arnopro);
            }
        }

        // Get sharedpreferences on expansions owned
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

        // Investigators counts how many investigators are still unused (to determine if the players have lost)
        int core = 0;
        int dunwich = 0;
        int carcosa = 0;
        int forgotten = 0;
        int circle = 0;
        int dream = 0;
        int norman = 0;
        int silas = 0;

        // Setup CheckBoxes with OnCheckedChangeListeners, as long as the investigator is not in use
        if (globalVariables.InvestigatorsInUse[Investigator.ROLAND_BANKS] == 0) {
            CheckBox roland = findViewById(R.id.roland_banks);
            roland.setVisibility(VISIBLE);
            roland.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            core++;
        }
        if (globalVariables.InvestigatorsInUse[Investigator.SKIDS_OTOOLE] == 0) {
            CheckBox skids = findViewById(R.id.skids_otoole);
            skids.setVisibility(VISIBLE);
            skids.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            core++;
        }
        if (globalVariables.InvestigatorsInUse[Investigator.AGNES_BAKER] == 0) {
            CheckBox agnes = findViewById(R.id.agnes_baker);
            agnes.setVisibility(VISIBLE);
            agnes.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            core++;
        }
        if (globalVariables.InvestigatorsInUse[Investigator.DAISY_WALKER] == 0) {
            CheckBox daisy = findViewById(R.id.daisy_walker);
            daisy.setVisibility(VISIBLE);
            daisy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            core++;
        }
        if (globalVariables.InvestigatorsInUse[Investigator.WENDY_ADAMS] == 0) {
            CheckBox wendy = findViewById(R.id.wendy_adams);
            wendy.setVisibility(VISIBLE);
            wendy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
        } else {
            core++;
        }
        // Hide the LinearLayout if all core investigators are dead
        if (core == 5) {
            coreCheckboxes.setVisibility(GONE);
        }

        // Setup checkboxes for Dunwich investigators
        if (dunwichOwned) {
            if (globalVariables.InvestigatorsInUse[Investigator.ZOEY_SAMARAS] == 0) {
                CheckBox zoey = findViewById(R.id.zoey_samaras);
                zoey.setVisibility(VISIBLE);
                zoey.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dunwich++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.REX_MURPHY] == 0) {
                CheckBox rex = findViewById(R.id.rex_murphy);
                rex.setVisibility(VISIBLE);
                rex.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dunwich++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.JENNY_BARNES] == 0) {
                CheckBox jenny = findViewById(R.id.jenny_barnes);
                jenny.setVisibility(VISIBLE);
                jenny.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dunwich++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.JIM_CULVER] == 0) {
                CheckBox jim = findViewById(R.id.jim_culver);
                jim.setVisibility(VISIBLE);
                jim.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dunwich++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.ASHCAN_PETE] == 0) {
                CheckBox pete = findViewById(R.id.ashcan_pete);
                pete.setVisibility(VISIBLE);
                pete.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dunwich++;
            }
        }
        // Hide LinearLayout if all Dunwich investigators are dead or if Dunwich is not owned
        if (dunwich == 5 || !dunwichOwned) {
            dunwichCheckboxes.setVisibility(GONE);
            dunwichButton.setVisibility(GONE);
        }

        // Setup checkboxes for Carcosa investigators
        if (carcosaOwned) {
            if (globalVariables.InvestigatorsInUse[Investigator.MARK_HARRIGAN] == 0) {
                CheckBox mark = findViewById(R.id.mark_harrigan);
                mark.setVisibility(VISIBLE);
                mark.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                carcosa++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.MINH_THI_PHAN] == 0) {
                CheckBox minh = findViewById(R.id.minh_thi_phan);
                minh.setVisibility(VISIBLE);
                minh.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                carcosa++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.SEFINA_ROUSSEAU] == 0) {
                CheckBox sefina = findViewById(R.id.sefina_rousseau);
                sefina.setVisibility(VISIBLE);
                sefina.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                carcosa++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.AKACHI_ONYELE] == 0) {
                CheckBox akachi = findViewById(R.id.akachi_onyele);
                akachi.setVisibility(VISIBLE);
                akachi.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                carcosa++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.WILLIAM_YORICK] == 0) {
                CheckBox william = findViewById(R.id.william_yorick);
                william.setVisibility(VISIBLE);
                william.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                carcosa++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.LOLA_HAYES] == 0) {
                CheckBox lola = findViewById(R.id.lola_hayes);
                lola.setVisibility(VISIBLE);
                lola.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                carcosa++;
            }
        }
        // Hide LinearLayout if all Dunwich investigators are dead or if Dunwich is not owned
        if (carcosa == 6 || !carcosaOwned) {
            carcosaCheckboxes.setVisibility(GONE);
            carcosaButton.setVisibility(GONE);
        }

        // Setup checkboxes for Forgotten Age investigators
        if (forgottenOwned) {
            if (globalVariables.InvestigatorsInUse[Investigator.LEO_ANDERSON] == 0) {
                CheckBox leo = findViewById(R.id.leo_anderson);
                leo.setVisibility(VISIBLE);
                leo.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                forgotten++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.URSULA_DOWNS] == 0) {
                CheckBox ursula = findViewById(R.id.ursula_downs);
                ursula.setVisibility(VISIBLE);
                ursula.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                forgotten++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.FINN_EDWARDS] == 0) {
                CheckBox finn = findViewById(R.id.finn_edwards);
                finn.setVisibility(VISIBLE);
                finn.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                forgotten++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.FATHER_MATEO] == 0) {
                CheckBox mateo = findViewById(R.id.father_mateo);
                mateo.setVisibility(VISIBLE);
                mateo.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                forgotten++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.CALVIN_WRIGHT] == 0) {
                CheckBox calvin = findViewById(R.id.calvin_wright);
                calvin.setVisibility(VISIBLE);
                calvin.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                forgotten++;
            }
        }
        // Hide LinearLayout if all Dunwich investigators are dead or if Dunwich is not owned
        if (forgotten == 5 || !forgottenOwned) {
            forgottenCheckboxes.setVisibility(GONE);
            forgottenButton.setVisibility(GONE);
        }

        // Setup checkboxes for Circle investigators
        if (circleOwned) {
            if (globalVariables.InvestigatorsInUse[Investigator.CAROLYN_FERN] == 0) {
                CheckBox carolyn = findViewById(R.id.carolyn_fern);
                carolyn.setVisibility(VISIBLE);
                carolyn.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                circle++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.JOE_DIAMOND] == 0) {
                CheckBox joe = findViewById(R.id.joe_diamond);
                joe.setVisibility(VISIBLE);
                joe.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                circle++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.PRESTON_FAIRMONT] == 0) {
                CheckBox preston = findViewById(R.id.preston_fairmont);
                preston.setVisibility(VISIBLE);
                preston.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                circle++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.DIANA_STANLEY] == 0) {
                CheckBox diana = findViewById(R.id.diana_stanley);
                diana.setVisibility(VISIBLE);
                diana.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                circle++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.RITA_YOUNG] == 0) {
                CheckBox rita = findViewById(R.id.rita_young);
                rita.setVisibility(VISIBLE);
                rita.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                circle++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.MARIE_LAMBEAU] == 0) {
                CheckBox marie = findViewById(R.id.marie_lambeau);
                marie.setVisibility(VISIBLE);
                marie.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                circle++;
            }
        }
        // Hide LinearLayout if all Circle investigators are dead or if Circle is not owned
        if (circle == 6 || !circleOwned) {
            circleCheckboxes.setVisibility(GONE);
            circleButton.setVisibility(GONE);
        }

        // Setup checkboxes for Dream investigators
        if (dreamOwned) {
            if (globalVariables.InvestigatorsInUse[Investigator.TOMMY_MULDOON] == 0) {
                CheckBox tommy = findViewById(R.id.tommy_muldoon);
                tommy.setVisibility(VISIBLE);
                tommy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dream++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.MANDY_THOMPSON] == 0) {
                CheckBox mandy = findViewById(R.id.mandy_thompson);
                mandy.setVisibility(VISIBLE);
                mandy.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dream++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.TONY_MORGAN] == 0) {
                CheckBox tony = findViewById(R.id.tony_morgan);
                tony.setVisibility(VISIBLE);
                tony.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dream++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.LUKE_ROBINSON] == 0) {
                CheckBox luke = findViewById(R.id.luke_robinson);
                luke.setVisibility(VISIBLE);
                luke.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dream++;
            }
            if (globalVariables.InvestigatorsInUse[Investigator.PATRICE_HATHAWAY] == 0) {
                CheckBox patrice = findViewById(R.id.patrice_hathaway);
                patrice.setVisibility(VISIBLE);
                patrice.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                dream++;
            }
        }
        // Hide LinearLayout if all Dream investigators are dead or if dream is not owned
        if (dream == 5 || !dreamOwned) {
            dreamCheckboxes.setVisibility(GONE);
            dreamButton.setVisibility(GONE);
        }

        boolean promoCheck = false;
        // Norman Withers
        if (normanOwned) {
            promoCheck = true;
            if (globalVariables.InvestigatorsInUse[Investigator.NORMAN_WITHERS] == 0) {
                CheckBox normanBox = findViewById(R.id.norman_withers);
                normanBox.setVisibility(VISIBLE);
                normanBox.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                norman++;
            }
        }

        // Silas Marsh
        if (silasOwned) {
            promoCheck = true;
            if (globalVariables.InvestigatorsInUse[Investigator.SILAS_MARSH] == 0) {
                CheckBox silasBox = findViewById(R.id.silas_marsh);
                silasBox.setVisibility(VISIBLE);
                silasBox.setOnCheckedChangeListener(new InvestigatorsCheckedChangeListener());
            } else {
                silas++;
            }
        }

        // Hide LinearLayout if all Dunwich investigators are dead or if Dunwich is not owned
        if (norman == 1 || !normanOwned) {
            normanCheckbox.setVisibility(GONE);
        }
        if (silas == 1 || !silasOwned) {
            silasCheckbox.setVisibility(GONE);
        }
        if(!promoCheck){
            promo.setVisibility(GONE);
        }

        // Edit stats button
        Button editStatsButton = findViewById(R.id.edit_stats_button);
        editStatsButton.setTypeface(teutonic);
        editStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTeamActivity.this, EditStatsActivity.class);
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
        continueButton.setOnClickListener(new SaveChangesListener());
    }


    /*
        Listener for the investigator checkboxes - Controls which investigators are shown in the current team
     */
    private class InvestigatorsCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            View parent = (View) buttonView.getParent().getParent().getParent().getParent().getParent();
            int removeInvestigator = -1;

            // For each investigator checkbox:
            //      If checked and fewer than 4 investigators, add it to the investigators
            //      If checked and 4 investigators, uncheck
            //      If unchecked, remove the investigator
            switch (buttonView.getId()) {
                case R.id.roland_banks:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.ROLAND_BANKS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.ROLAND_BANKS) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.skids_otoole:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.SKIDS_OTOOLE);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.SKIDS_OTOOLE) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.agnes_baker:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.AGNES_BAKER);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.AGNES_BAKER) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.daisy_walker:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.DAISY_WALKER);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.DAISY_WALKER) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.wendy_adams:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.WENDY_ADAMS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.WENDY_ADAMS) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.zoey_samaras:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.ZOEY_SAMARAS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.ZOEY_SAMARAS) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.rex_murphy:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.REX_MURPHY);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.REX_MURPHY) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.jenny_barnes:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.JENNY_BARNES);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.JENNY_BARNES) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.jim_culver:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.JIM_CULVER);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.JIM_CULVER) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.ashcan_pete:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.ASHCAN_PETE);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.ASHCAN_PETE) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.mark_harrigan:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.MARK_HARRIGAN);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.MARK_HARRIGAN) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.minh_thi_phan:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.MINH_THI_PHAN);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.MINH_THI_PHAN) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.sefina_rousseau:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.SEFINA_ROUSSEAU);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.SEFINA_ROUSSEAU) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.akachi_onyele:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.AKACHI_ONYELE);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.AKACHI_ONYELE) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.william_yorick:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.WILLIAM_YORICK);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.WILLIAM_YORICK) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.lola_hayes:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.LOLA_HAYES);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.LOLA_HAYES) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.leo_anderson:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.LEO_ANDERSON);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.LEO_ANDERSON) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.ursula_downs:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.URSULA_DOWNS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.URSULA_DOWNS) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.finn_edwards:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.FINN_EDWARDS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.FINN_EDWARDS) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.father_mateo:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.FATHER_MATEO);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.FATHER_MATEO) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.calvin_wright:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.CALVIN_WRIGHT);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.CALVIN_WRIGHT) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.marie_lambeau:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.MARIE_LAMBEAU);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.MARIE_LAMBEAU) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.norman_withers:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.NORMAN_WITHERS);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.NORMAN_WITHERS) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.carolyn_fern:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.CAROLYN_FERN);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.CAROLYN_FERN) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.silas_marsh:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.SILAS_MARSH);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.SILAS_MARSH) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.joe_diamond:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.JOE_DIAMOND);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.JOE_DIAMOND) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.preston_fairmont:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.PRESTON_FAIRMONT);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.PRESTON_FAIRMONT) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.diana_stanley:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.DIANA_STANLEY);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.DIANA_STANLEY) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.rita_young:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.RITA_YOUNG);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.RITA_YOUNG) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.tommy_muldoon:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.TOMMY_MULDOON);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.TOMMY_MULDOON) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.mandy_thompson:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.MANDY_THOMPSON);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.MANDY_THOMPSON) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.tony_morgan:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.TONY_MORGAN);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.TONY_MORGAN) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.luke_robinson:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.LUKE_ROBINSON);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.LUKE_ROBINSON) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                case R.id.patrice_hathaway:
                    if (isChecked && investigatorsCount < 4) {
                        globalVariables.InvestigatorNames.add(Investigator.PATRICE_HATHAWAY);
                        investigatorsCount++;
                    } else if (isChecked) {
                        buttonView.setChecked(false);
                    } else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (globalVariables.InvestigatorNames.get(i) == Investigator.PATRICE_HATHAWAY) {
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
                // Default controls for saved investigator checkboxes
                //      If checked and fewer than four investigators then adds the investigator
                //      If checked and four investigators, unchecks the box
                //      If unchecked, removes the investigator
                default:
                    if (isChecked && investigatorsCount < 4) {
                        investigatorsCount++;
                        // If one of the currently active investigators, adds it and matches the correct investigator
                        // to the saved investigator
                        if (buttonView.getId() < 100) {
                            globalVariables.InvestigatorNames.add(globalVariables.Investigators.get(buttonView.getId())
                                    .Name);
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == globalVariables.Investigators.get
                                        (buttonView.getId()).Name) {
                                    switch (i) {
                                        case 0:
                                            investigatorOne = globalVariables.Investigators.get(buttonView.getId());
                                            break;
                                        case 1:
                                            investigatorTwo = globalVariables.Investigators.get(buttonView.getId());
                                            break;
                                        case 2:
                                            investigatorThree = globalVariables.Investigators.get(buttonView.getId());
                                            break;
                                        case 3:
                                            investigatorFour = globalVariables.Investigators.get(buttonView.getId());
                                            break;
                                    }
                                }
                            }
                        }
                        // If a saved but inactive investigator, adds it and matches the correct investigator to the
                        // saved investigator
                        else {
                            globalVariables.InvestigatorNames.add(globalVariables.SavedInvestigators.get(buttonView
                                    .getId() - 100).Name);
                            for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                                if (globalVariables.InvestigatorNames.get(i) == globalVariables.SavedInvestigators.get
                                        (buttonView.getId() - 100).Name) {
                                    switch (i) {
                                        case 0:
                                            investigatorOne = globalVariables.SavedInvestigators.get(buttonView.getId
                                                    () - 100);
                                            break;
                                        case 1:
                                            investigatorTwo = globalVariables.SavedInvestigators.get(buttonView.getId
                                                    () - 100);
                                            break;
                                        case 2:
                                            investigatorThree = globalVariables.SavedInvestigators.get(buttonView
                                                    .getId() - 100);
                                            break;
                                        case 3:
                                            investigatorFour = globalVariables.SavedInvestigators.get(buttonView
                                                    .getId() - 100);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                    // Unchecks if four investigators
                    else if (isChecked) {
                        buttonView.setChecked(false);
                    }
                    // Removes investigator if unchecked
                    else {
                        investigatorsCount--;
                        for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                            if (buttonView.getId() >= 100) {
                                // Removes the investigator
                                if (globalVariables.InvestigatorNames.get(i) == globalVariables.SavedInvestigators.get
                                        (buttonView.getId() - 100).Name) {
                                    removeInvestigator = i;
                                    globalVariables.InvestigatorNames.remove(i);
                                }
                            } else if (globalVariables.InvestigatorNames.get(i) == globalVariables.Investigators.get
                                    (buttonView.getId()).Name) {
                                // Removes the investigator
                                removeInvestigator = i;
                                globalVariables.InvestigatorNames.remove(i);
                            }
                        }
                    }
                    break;
            }
            // Refactor the investigators if an investigator has been removed
            if (removeInvestigator > -1) {
                switch (removeInvestigator) {
                    case 0:
                        investigatorOne = investigatorTwo;
                        globalVariables.PlayerNames[0] = globalVariables.PlayerNames[1];
                        globalVariables.DeckNames[0] = globalVariables.DeckNames[1];
                        globalVariables.DeckLists[0] = globalVariables.DeckLists[1];
                    case 1:
                        investigatorTwo = investigatorThree;
                        globalVariables.PlayerNames[1] = globalVariables.PlayerNames[2];
                        globalVariables.DeckNames[1] = globalVariables.DeckNames[2];
                        globalVariables.DeckLists[1] = globalVariables.DeckLists[2];
                    case 2:
                        investigatorThree = investigatorFour;
                        globalVariables.PlayerNames[2] = globalVariables.PlayerNames[3];
                        globalVariables.DeckNames[2] = globalVariables.DeckNames[3];
                        globalVariables.DeckLists[2] = globalVariables.DeckLists[3];
                    case 3:
                        investigatorFour = null;
                        globalVariables.PlayerNames[3] = null;
                        globalVariables.DeckNames[3] = null;
                        globalVariables.DeckLists[3] = null;
                }
            }

            // Get the relevant views
            LinearLayout investigatorOneLayout = parent.findViewById(R.id.investigator_one);
            LinearLayout investigatorTwoLayout = parent.findViewById(R.id.investigator_two);
            LinearLayout investigatorThreeLayout = parent.findViewById(R.id.investigator_three);
            LinearLayout investigatorFourLayout = parent.findViewById(R.id.investigator_four);
            TextView investigatorOneName = parent.findViewById(R.id.investigator_one_name);
            TextView investigatorTwoName = parent.findViewById(R.id.investigator_two_name);
            TextView investigatorThreeName = parent.findViewById(R.id.investigator_three_name);
            TextView investigatorFourName = parent.findViewById(R.id.investigator_four_name);
            TextView investigatorOneLink = parent.findViewById(R.id.investigator_one_link);
            TextView investigatorTwoLink = parent.findViewById(R.id.investigator_two_link);
            TextView investigatorThreeLink = parent.findViewById(R.id.investigator_three_link);
            TextView investigatorFourLink = parent.findViewById(R.id.investigator_four_link);
            String[] investigatorNames = getResources().getStringArray(investigators);

            // Set fonts
            Typeface arnoprobold = Typeface.createFromAsset(getAssets(), "fonts/arnoprobold.otf");
            Typeface wolgast = Typeface.createFromAsset(getAssets(), "fonts/wolgast.otf");
            investigatorOneName.setTypeface(arnoprobold);
            investigatorTwoName.setTypeface(arnoprobold);
            investigatorThreeName.setTypeface(arnoprobold);
            investigatorFourName.setTypeface(arnoprobold);
            investigatorOneLink.setTypeface(wolgast);
            investigatorOneLink.setPaintFlags(investigatorOneLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            investigatorOneLink.setOnClickListener(new investigatorLinkListener());
            investigatorTwoLink.setTypeface(wolgast);
            investigatorTwoLink.setPaintFlags(investigatorTwoLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            investigatorTwoLink.setOnClickListener(new investigatorLinkListener());
            investigatorThreeLink.setTypeface(wolgast);
            investigatorThreeLink.setPaintFlags(investigatorThreeLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            investigatorThreeLink.setOnClickListener(new investigatorLinkListener());
            investigatorFourLink.setTypeface(wolgast);
            investigatorFourLink.setPaintFlags(investigatorFourLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            investigatorFourLink.setOnClickListener(new investigatorLinkListener());

            // Show relevant views
            if (investigatorOne != null) {
                investigatorOneLayout.setVisibility(VISIBLE);
                Investigator currentInvestigator = investigatorOne;
                String nameOne = investigatorNames[currentInvestigator.Name];
                investigatorOneName.setText(nameOne);
                if (currentInvestigator.PlayerName != null && currentInvestigator.PlayerName.length() > 0) {
                    String playerName = currentInvestigator.PlayerName + " ";
                    investigatorOneLink.setText(playerName);
                } else {
                    investigatorOneLink.setText(R.string.edit_info);
                }
            } else if (investigatorsCount > 0) {
                investigatorOneLayout.setVisibility(VISIBLE);
                String nameOne = investigatorNames[globalVariables.InvestigatorNames.get(0)];
                investigatorOneName.setText(nameOne);
                if (globalVariables.PlayerNames[0] != null) {
                    if (globalVariables.PlayerNames[0].length() > 0) {
                        investigatorOneLink.setText(globalVariables.PlayerNames[0]);
                    } else {
                        investigatorOneLink.setText(R.string.edit_info);
                    }
                } else {
                    investigatorOneLink.setText(R.string.edit_info);
                }
            } else {
                investigatorOneLayout.setVisibility(GONE);
            }

            if (investigatorTwo != null) {
                investigatorTwoLayout.setVisibility(VISIBLE);
                Investigator currentInvestigator = investigatorTwo;
                String nameTwo = investigatorNames[currentInvestigator.Name];
                investigatorTwoName.setText(nameTwo);
                if (currentInvestigator.PlayerName != null && currentInvestigator.PlayerName.length() > 0) {
                    String playerName = currentInvestigator.PlayerName + " ";
                    investigatorTwoLink.setText(playerName);
                } else {
                    investigatorTwoLink.setText(R.string.edit_info);
                }
            } else if (investigatorsCount > 1) {
                investigatorTwoLayout.setVisibility(VISIBLE);
                String nameTwo = investigatorNames[globalVariables.InvestigatorNames.get(1)];
                investigatorTwoName.setText(nameTwo);
                if (globalVariables.PlayerNames[1] != null) {
                    if (globalVariables.PlayerNames[1].length() > 0) {
                        investigatorTwoLink.setText(globalVariables.PlayerNames[1]);
                    } else {
                        investigatorTwoLink.setText(R.string.edit_info);
                    }
                } else {
                    investigatorTwoLink.setText(R.string.edit_info);
                }
            } else {
                investigatorTwoLayout.setVisibility(GONE);
            }

            if (investigatorThree != null) {
                investigatorThreeLayout.setVisibility(VISIBLE);
                Investigator currentInvestigator = investigatorThree;
                String nameThree = investigatorNames[currentInvestigator.Name];
                investigatorThreeName.setText(nameThree);
                if (currentInvestigator.PlayerName != null && currentInvestigator.PlayerName.length() > 0) {
                    String playerName = currentInvestigator.PlayerName + " ";
                    investigatorThreeLink.setText(playerName);
                } else {
                    investigatorThreeLink.setText(R.string.edit_info);
                }
            } else if (investigatorsCount > 2) {
                investigatorThreeLayout.setVisibility(VISIBLE);
                String nameThree = investigatorNames[globalVariables.InvestigatorNames.get(2)];
                investigatorThreeName.setText(nameThree);
                if (globalVariables.PlayerNames[2] != null) {
                    if (globalVariables.PlayerNames[2].length() > 0) {
                        investigatorThreeLink.setText(globalVariables.PlayerNames[2]);
                    } else {
                        investigatorThreeLink.setText(R.string.edit_info);
                    }
                } else {
                    investigatorThreeLink.setText(R.string.edit_info);
                }
            } else {
                investigatorThreeLayout.setVisibility(GONE);
            }

            if (investigatorFour != null) {
                investigatorFourLayout.setVisibility(VISIBLE);
                Investigator currentInvestigator = investigatorFour;
                String nameFour = investigatorNames[currentInvestigator.Name];
                investigatorFourName.setText(nameFour);
                if (currentInvestigator.PlayerName != null && currentInvestigator.PlayerName.length() > 0) {
                    String playerName = currentInvestigator.PlayerName + " ";
                    investigatorFourLink.setText(playerName);
                } else {
                    investigatorFourLink.setText(R.string.edit_info);
                }
            } else if (investigatorsCount > 3) {
                investigatorFourLayout.setVisibility(VISIBLE);
                String nameFour = investigatorNames[globalVariables.InvestigatorNames.get(3)];
                investigatorFourName.setText(nameFour);
                if (globalVariables.PlayerNames[3] != null) {
                    if (globalVariables.PlayerNames[3].length() > 0) {
                        investigatorFourLink.setText(globalVariables.PlayerNames[3]);
                    } else {
                        investigatorFourLink.setText(R.string.edit_info);
                    }
                } else {
                    investigatorFourLink.setText(R.string.edit_info);
                }
            } else {
                investigatorFourLayout.setVisibility(GONE);
            }
        }
    }


    // Opens a dialog to enter all of the relevant information
    private class investigatorLinkListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            DialogFragment investigatorInfoFragment = new InvestigatorInfoDialog();
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
    public static class InvestigatorInfoDialog extends DialogFragment {

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
            String[] investigatorNames = getResources().getStringArray(investigators);
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
            Typeface teutonic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/teutonic.ttf");
            Button cancelButton = v.findViewById(R.id.cancel_button);
            Button okayButton = v.findViewById(R.id.okay_button);
            cancelButton.setTypeface(teutonic);
            okayButton.setTypeface(teutonic);

            // Set text into edittexts
            switch (investigator) {
                case 0:
                    if (investigatorOne != null) {
                        Investigator currentInvestigator = investigatorOne;
                        if (currentInvestigator.PlayerName != null) {
                            playerName.setText(currentInvestigator.PlayerName);
                        }
                        if (currentInvestigator.DeckName != null) {
                            playerDeck.setText(currentInvestigator.DeckName);
                        }
                        if (currentInvestigator.Decklist != null) {
                            playerLink.setText(currentInvestigator.Decklist);
                        }
                    } else {
                        playerName.setText(globalVariables.PlayerNames[investigator]);
                        playerDeck.setText(globalVariables.DeckNames[investigator]);
                        playerLink.setText(globalVariables.DeckLists[investigator]);
                    }
                    break;
                case 1:
                    if (investigatorTwo != null) {
                        Investigator currentInvestigator = investigatorTwo;
                        if (currentInvestigator.PlayerName != null) {
                            playerName.setText(currentInvestigator.PlayerName);
                        }
                        if (currentInvestigator.DeckName != null) {
                            playerDeck.setText(currentInvestigator.DeckName);
                        }
                        if (currentInvestigator.Decklist != null) {
                            playerLink.setText(currentInvestigator.Decklist);
                        }
                    } else {
                        playerName.setText(globalVariables.PlayerNames[investigator]);
                        playerDeck.setText(globalVariables.DeckNames[investigator]);
                        playerLink.setText(globalVariables.DeckLists[investigator]);
                    }
                    break;
                case 2:
                    if (investigatorThree != null) {
                        Investigator currentInvestigator = investigatorThree;
                        if (currentInvestigator.PlayerName != null) {
                            playerName.setText(currentInvestigator.PlayerName);
                        }
                        if (currentInvestigator.DeckName != null) {
                            playerDeck.setText(currentInvestigator.DeckName);
                        }
                        if (currentInvestigator.Decklist != null) {
                            playerLink.setText(currentInvestigator.Decklist);
                        }
                    } else {
                        playerName.setText(globalVariables.PlayerNames[investigator]);
                        playerDeck.setText(globalVariables.DeckNames[investigator]);
                        playerLink.setText(globalVariables.DeckLists[investigator]);
                    }
                    break;
                case 3:
                    if (investigatorFour != null) {
                        Investigator currentInvestigator = investigatorFour;
                        if (currentInvestigator.PlayerName != null) {
                            playerName.setText(currentInvestigator.PlayerName);
                        }
                        if (currentInvestigator.DeckName != null) {
                            playerDeck.setText(currentInvestigator.DeckName);
                        }
                        if (currentInvestigator.Decklist != null) {
                            playerLink.setText(currentInvestigator.Decklist);
                        }
                    } else {
                        playerName.setText(globalVariables.PlayerNames[investigator]);
                        playerDeck.setText(globalVariables.DeckNames[investigator]);
                        playerLink.setText(globalVariables.DeckLists[investigator]);
                    }
                    break;
            }

            okayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Save the strings to the relevant variables
                    String name = playerName.getText().toString().trim();
                    String deck = playerDeck.getText().toString().trim();
                    String list = playerLink.getText().toString().trim();
                    switch (investigator) {
                        case 0:
                            if (investigatorOne != null) {
                                investigatorOne.PlayerName = name;
                                investigatorOne.DeckName = deck;
                                investigatorOne.Decklist = list;
                            } else {
                                globalVariables.PlayerNames[0] = name;
                                globalVariables.DeckNames[0] = deck;
                                globalVariables.DeckLists[0] = list;
                            }
                            break;
                        case 1:
                            if (investigatorTwo != null) {
                                investigatorTwo.PlayerName = name;
                                investigatorTwo.DeckName = deck;
                                investigatorTwo.Decklist = list;
                            } else {
                                globalVariables.PlayerNames[1] = name;
                                globalVariables.DeckNames[1] = deck;
                                globalVariables.DeckLists[1] = list;
                            }
                            break;
                        case 2:
                            if (investigatorThree != null) {
                                investigatorThree.PlayerName = name;
                                investigatorThree.DeckName = deck;
                                investigatorThree.Decklist = list;
                            } else {
                                globalVariables.PlayerNames[2] = name;
                                globalVariables.DeckNames[2] = deck;
                                globalVariables.DeckLists[2] = list;
                            }
                            break;
                        case 3:
                            if (investigatorFour != null) {
                                investigatorFour.PlayerName = name;
                                investigatorFour.DeckName = deck;
                                investigatorFour.Decklist = list;
                            } else {
                                globalVariables.PlayerNames[3] = name;
                                globalVariables.DeckNames[3] = deck;
                                globalVariables.DeckLists[3] = list;
                            }
                            break;
                    }

                    // Sets the player's name to the relevant textview in the core activity
                    TextView investigatorLink = getActivity().findViewById(R.id.investigator_one_link);
                    String investigatorName = " ";
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
                    if (globalVariables.PlayerNames[investigator] != null) {
                        investigatorName = globalVariables.PlayerNames[investigator] + " ";
                    } else {
                        switch (investigator) {
                            case 0:
                                investigatorName = investigatorOne.PlayerName + " ";
                                break;
                            case 1:
                                investigatorName = investigatorTwo.PlayerName + " ";
                                break;
                            case 2:
                                investigatorName = investigatorThree.PlayerName + " ";
                                break;
                            case 3:
                                investigatorName = investigatorFour.PlayerName + " ";
                                break;
                        }
                    }
                    if (!investigatorName.equals(" ") && investigatorName.length() > 0) {
                        investigatorLink.setText(investigatorName);
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

    // Saves any changes made and advances back to the current team screen
    private class SaveChangesListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (investigatorsCount == 0) {
                // Show toast if no investigators are selected
                Toast toast = Toast.makeText(getBaseContext(), R.string.must_investigator, Toast
                        .LENGTH_SHORT);
                toast.show();
            } else {

                // Remove dead investigators
                for (int i = globalVariables.Investigators.size() - 1; i >= 0; i--) {
                    if (globalVariables.Investigators.get(i).Status == 2) {
                        globalVariables.Investigators.remove(i);
                    }
                }

                // Add investigators who have been selected
                for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                    for (int a = 0; a < globalVariables.Investigators.size(); a++) {
                        if (globalVariables.Investigators.get(a).Name == globalVariables.InvestigatorNames.get(i)) {
                            globalVariables.Investigators.get(a).Status = 999;
                        }
                    }
                    for (int a = 0; a < globalVariables.SavedInvestigators.size(); a++) {
                        if (globalVariables.SavedInvestigators.get(a).Name == globalVariables.InvestigatorNames.get
                                (i)) {
                            globalVariables.SavedInvestigators.get(a).Status = 999;
                        }
                    }
                }
                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                    if (globalVariables.Investigators.get(i).Status != 999) {
                        globalVariables.Investigators.get(i).Status = 3;
                        globalVariables.SavedInvestigators.add(globalVariables.Investigators.get(i));
                    }
                }
                for (int i = globalVariables.SavedInvestigators.size() - 1; i >= 0; i--) {
                    if (globalVariables.SavedInvestigators.get(i).Status == 999) {
                        globalVariables.SavedInvestigators.remove(i);
                    }
                }

                globalVariables.Investigators.clear();
                if (investigatorOne != null) {
                    globalVariables.Investigators.add(investigatorOne);
                }
                if (investigatorTwo != null) {
                    globalVariables.Investigators.add(investigatorTwo);
                }
                if (investigatorThree != null) {
                    globalVariables.Investigators.add(investigatorThree);
                }
                if (investigatorFour != null) {
                    globalVariables.Investigators.add(investigatorFour);
                }

                for (int i = 0; i < globalVariables.InvestigatorNames.size(); i++) {
                    if (globalVariables.InvestigatorsInUse[globalVariables.InvestigatorNames.get(i)] == 0) {
                        globalVariables.Investigators.add(new Investigator(globalVariables.InvestigatorNames.get(i),
                                globalVariables.PlayerNames[i], globalVariables.DeckNames[i], globalVariables
                                .DeckLists[i]));
                    }
                    globalVariables.InvestigatorsInUse[globalVariables.InvestigatorNames.get(i)] = 1;
                }
                for (int i = 0; i < globalVariables.Investigators.size(); i++) {
                    globalVariables.Investigators.get(i).Status = 1;
                }

                globalVariables.InvestigatorNames.clear();
                globalVariables.PlayerNames = new String[4];
                globalVariables.DeckNames = new String[4];
                globalVariables.DeckLists = new String[4];

                // Save the campaign
                ScenarioResolutionActivity.saveCampaign(view.getContext(), globalVariables);

                // Go back to the current team
                Intent intent = new Intent(EditTeamActivity.this, ScenarioMainActivity.class);
                startActivity(intent);
                finish();
            }
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
