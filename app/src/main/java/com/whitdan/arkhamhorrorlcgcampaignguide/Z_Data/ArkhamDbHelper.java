package com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.CampaignEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.CarcosaEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.ChaosBagEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.DunwichEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.ForgottenEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.InvestigatorEntry;
import com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.NightEntry;

import static com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data.ArkhamContract.InvestigatorEntry.INVESTIGATOR_ID;

/**
 * Custom SQLiteOpenHelper that creates the tables defined in the contract.
 * Currently there are three tables:
 * campaigns - contains all global variables
 * investigators - contains a row per investigator, with all relevant variables
 * night - contains all variables specific to the Night of the Zealot campaign
 */

public class ArkhamDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "campaigns.db";
    private static final int DATABASE_VERSION = 33;

    public ArkhamDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
            Create Strings that contain the SQL statements to create the necessary tables
         */

        // Campaigns table
        String SQL_CREATE_CAMPAIGNS_TABLE = "CREATE TABLE " + CampaignEntry.TABLE_NAME + " ("
                + CampaignEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CampaignEntry.COLUMN_CAMPAIGN_VERSION + " INTEGER NOT NULL, "
                + CampaignEntry.COLUMN_CAMPAIGN_NAME + " STRING NOT NULL, "
                + CampaignEntry.COLUMN_CHAOS_BAG + " INTEGER NOT NULL, "
                + CampaignEntry.COLUMN_CURRENT_CAMPAIGN + " INTEGER NOT NULL, "
                + CampaignEntry.COLUMN_CURRENT_SCENARIO + " INTEGER NOT NULL, "
                + CampaignEntry.COLUMN_DIFFICULTY + " INTEGER, "
                + CampaignEntry.COLUMN_NOTES + " STRING, "
                + CampaignEntry.COLUMN_NIGHT_COMPLETED + " INTEGER, "
                + CampaignEntry.COLUMN_DUNWICH_COMPLETED + " INTEGER, "
                + CampaignEntry.COLUMN_CARCOSA_COMPLETED + " INTEGER, "
                + CampaignEntry.COLUMN_ROLAND_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_DAISY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_SKIDS_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_AGNES_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_WENDY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_ZOEY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_REX_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_JENNY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_JIM_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_PETE_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_MARK_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_MINH_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_SEFINA_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_AKACHI_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_WILLIAM_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_LOLA_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_MARIE_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_NORMAN_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_CAROLYN_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_SILAS_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_LEO_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_URSULA_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_FINN_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_MATEO_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_CALVIN_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_JOE_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_PRESTON_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_DIANA_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_RITA_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_GAVRIELLA_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_JEROME_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_PENNY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_VALENTINO_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_TOMMY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_MANDY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_TONY_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_LUKE_INUSE + " INTEGER, "
                + CampaignEntry.COLUMN_PATRICE_HATHAWAY + " INTEGER, "
                + CampaignEntry.COLUMN_ROUGAROU_STATUS + " INTEGER, "
                + CampaignEntry.COLUMN_STRANGE_SOLUTION + " INTEGER, "
                + CampaignEntry.COLUMN_ARCHAIC_GLYPHS + " INTEGER, "
                + CampaignEntry.COLUMN_CHARONS_OBOL + " INTEGER, "
                + CampaignEntry.COLUMN_ANCIENT_STONE + " INTEGER, "
                + CampaignEntry.COLUMN_DOOMED + " INTEGER, "
                + CampaignEntry.COLUMN_CARNEVALE_STATUS + " INTEGER, "
                + CampaignEntry.COLUMN_CARNEVALE_REWARDS + " INTEGER);";

        // Investigators table
        String SQL_CREATE_INVESTIGATORS_TABLE = "CREATE TABLE " + InvestigatorEntry.TABLE_NAME + " ("
                + InvestigatorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InvestigatorEntry.PARENT_ID + " INTEGER NOT NULL, "
                + INVESTIGATOR_ID + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_NAME + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_STATUS + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_DAMAGE + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_HORROR + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_TOTAL_XP + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_AVAILABLE_XP + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_SPENT_XP + " INTEGER NOT NULL, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_PLAYER + " STRING, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_DECKNAME + " STRING, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_DECKLIST + " STRING, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_PROVISIONS + " INTEGER, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_MEDICINE + " INTEGER, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_SUPPLIES + " INTEGER, "
                + InvestigatorEntry.COLUMN_INVESTIGATOR_RESUPPLIES_ONE + " INTEGER);";

        // Night of the Zealot table
        String SQL_CREATE_NIGHT_TABLE = "CREATE TABLE " + NightEntry.TABLE_NAME + " ("
                + NightEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NightEntry.PARENT_ID + " INTEGER NOT NULL, "
                + NightEntry.COLUMN_HOUSE_STANDING + " INTEGER, "
                + NightEntry.COLUMN_GHOUL_PRIEST + " INTEGER, "
                + NightEntry.COLUMN_LITA_STATUS + " INTEGER, "
                + NightEntry.COLUMN_MIDNIGHT_STATUS + " INTEGER, "
                + NightEntry.COLUMN_CULTISTS_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_DREW_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_HERMAN_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_PETER_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_VICTORIA_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_RUTH_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_MASKED_INTERROGATED + " INTEGER, "
                + NightEntry.COLUMN_UMORDHOTH_STATUS + " INTEGER);";

        // The Dunwich Legacy table
        String SQL_CREATE_DUNWICH_TABLE = "CREATE TABLE " + DunwichEntry.TABLE_NAME + " ("
                + DunwichEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DunwichEntry.PARENT_ID + " INTEGER NOT NULL, "
                + DunwichEntry.COLUMN_FIRST_SCENARIO + " INTEGER, "
                + DunwichEntry.COLUMN_INVESTIGATORS_UNCONSCIOUS + " INTEGER, "
                + DunwichEntry.COLUMN_HENRY_ARMITAGE + " INTEGER, "
                + DunwichEntry.COLUMN_WARREN_RICE + " INTEGER, "
                + DunwichEntry.COLUMN_STUDENTS + " INTEGER, "
                + DunwichEntry.COLUMN_FRANCIS_MORGAN + " INTEGER, "
                + DunwichEntry.COLUMN_OBANNION_GANG + " INTEGER, "
                + DunwichEntry.COLUMN_INVESTIGATORS_CHEATED + " INTEGER, "
                + DunwichEntry.COLUMN_NECRONOMICON + " INTEGER, "
                + DunwichEntry.COLUMN_ADAM_LYNCH_HAROLD_WALSTED + " INTEGER, "
                + DunwichEntry.COLUMN_DELAYED + " INTEGER, "
                + DunwichEntry.COLUMN_SILAS_BISHOP + " INTEGER, "
                + DunwichEntry.COLUMN_ZEBULON_WHATELEY + " INTEGER, "
                + DunwichEntry.COLUMN_EARL_SAWYER + " INTEGER, "
                + DunwichEntry.COLUMN_ALLY_SACRIFICED + " INTEGER, "
                + DunwichEntry.COLUMN_TOWNSFOLK + " INTEGER, "
                + DunwichEntry.COLUMN_BROOD_ESCAPED + " INTEGER, "
                + DunwichEntry.COLUMN_INVESTIGATORS_GATE + " INTEGER, "
                + DunwichEntry.COLUMN_YOG_SOTHOTH + " INTEGER);";

        // Path to Carcosa table
        String SQL_CREATE_CARCOSA_TABLE = "CREATE TABLE " + CarcosaEntry.TABLE_NAME + " ("
                + CarcosaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CarcosaEntry.PARENT_ID + " INTEGER NOT NULL, "
                + CarcosaEntry.COLUMN_DOUBT + " INTEGER, "
                + CarcosaEntry.COLUMN_CONVICTION + " INTEGER, "
                + CarcosaEntry.COLUMN_CHASING_STRANGER + " INTEGER, "
                + CarcosaEntry.COLUMN_STRANGER + " INTEGER, "
                + CarcosaEntry.COLUMN_POLICE + " INTEGER, "
                + CarcosaEntry.COLUMN_THEATRE + " INTEGER, "
                + CarcosaEntry.COLUMN_CONSTANCE + " INTEGER, "
                + CarcosaEntry.COLUMN_JORDAN + " INTEGER, "
                + CarcosaEntry.COLUMN_ISHIMARU + " INTEGER, "
                + CarcosaEntry.COLUMN_SEBASTIEN + " INTEGER, "
                + CarcosaEntry.COLUMN_ASHLEIGH + " INTEGER, "
                + CarcosaEntry.COLUMN_PARTY + " INTEGER, "
                + CarcosaEntry.COLUMN_ONYX + " INTEGER, "
                + CarcosaEntry.COLUMN_ASYLUM + " INTEGER, "
                + CarcosaEntry.COLUMN_DANIEL + " INTEGER, "
                + CarcosaEntry.COLUMN_DANIELS_WARNING + " INTEGER, "
                + CarcosaEntry.COLUMN_DREAMS + " INTEGER NOT NULL, "
                + CarcosaEntry.COLUMN_NIGEL + " INTEGER, "
                + CarcosaEntry.COLUMN_INV_ONE_READ_ACT + " INTEGER, "
                + CarcosaEntry.COLUMN_INV_TWO_READ_ACT + " INTEGER, "
                + CarcosaEntry.COLUMN_INV_THREE_READ_ACT + " INTEGER, "
                + CarcosaEntry.COLUMN_INV_FOUR_READ_ACT + " INTEGER, "
                + CarcosaEntry.COLUMN_PATH + " INTEGER, "
                + CarcosaEntry.COLUMN_HASTUR + " INTEGER, "
                + CarcosaEntry.COLUMN_INV_ONE_POSSESSED + " INTEGER, "
                + CarcosaEntry.COLUMN_INV_TWO_POSSESSED + " INTEGER, "
                + CarcosaEntry.COLUMN_INV_THREE_POSSESSED + " INTEGER, "
                + CarcosaEntry.COLUMN_INV_FOUR_POSSESSED + " INTEGER);";

        // The Forgotten Age table
        String SQL_CREATE_FORGOTTEN_TABLE = "CREATE TABLE " + ForgottenEntry.TABLE_NAME + " ("
                + ForgottenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ForgottenEntry.PARENT_ID + " INTEGER NOT NULL, "
                + ForgottenEntry.COLUMN_YIGS_FURY + " INTEGER, "
                + ForgottenEntry.COLUMN_RUINS + " INTEGER, "
                + ForgottenEntry.COLUMN_ICHTACA + " INTEGER, "
                + ForgottenEntry.COLUMN_ALEJANDRO + " INTEGER, "
                + ForgottenEntry.COLUMN_LOW_RATIONS + " INTEGER, "
                + ForgottenEntry.COLUMN_RELIC + " INTEGER, "
                + ForgottenEntry.COLUMN_HARBINGER + " INTEGER, "
                + ForgottenEntry.COLUMN_EZTLI + " INTEGER, "
                + ForgottenEntry.COLUMN_CUSTODY + " INTEGER, "
                + ForgottenEntry.COLUMN_ICHTACAS_TALE + " INTEGER, "
                + ForgottenEntry.COLUMN_MISSING_RELIC + " INTEGER, "
                + ForgottenEntry.COLUMN_MISSING_ALEJANDRO + " INTEGER, "
                + ForgottenEntry.COLUMN_MISSING_ICHTACA + " INTEGER, "
                + ForgottenEntry.COLUMN_GASOLINE_USED + " INTEGER, "
                + ForgottenEntry.COLUMN_PATHS_KNOWN + " INTEGER, "
                + ForgottenEntry.COLUMN_ICHTACA_CONFIDENCE + " INTEGER);";

        // Chaos bag table
        String SQL_CREATE_CHAOS_BAG_TABLE = "CREATE TABLE " + ChaosBagEntry.TABLE_NAME + " ("
                + ChaosBagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ChaosBagEntry.COLUMN_CHAOS_BAG_NAME + " STRING NOT NULL, "
                + ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS + " INTEGER NOT NULL, "
                + ChaosBagEntry.COLUMN_ONE + " INTEGER, "
                + ChaosBagEntry.COLUMN_TWO + " INTEGER, "
                + ChaosBagEntry.COLUMN_THREE + " INTEGER, "
                + ChaosBagEntry.COLUMN_FOUR + " INTEGER, "
                + ChaosBagEntry.COLUMN_FIVE + " INTEGER, "
                + ChaosBagEntry.COLUMN_SIX + " INTEGER, "
                + ChaosBagEntry.COLUMN_SEVEN + " INTEGER, "
                + ChaosBagEntry.COLUMN_EIGHT + " INTEGER, "
                + ChaosBagEntry.COLUMN_NINE + " INTEGER, "
                + ChaosBagEntry.COLUMN_TEN + " INTEGER, "
                + ChaosBagEntry.COLUMN_ELEVEN + " INTEGER, "
                + ChaosBagEntry.COLUMN_TWELVE + " INTEGER, "
                + ChaosBagEntry.COLUMN_THIRTEEN + " INTEGER, "
                + ChaosBagEntry.COLUMN_FOURTEEN + " INTEGER, "
                + ChaosBagEntry.COLUMN_FIFTEEN + " INTEGER, "
                + ChaosBagEntry.COLUMN_SIXTEEN + " INTEGER);";

        // Execute the SQL statements
        db.execSQL(SQL_CREATE_CAMPAIGNS_TABLE);
        db.execSQL(SQL_CREATE_INVESTIGATORS_TABLE);
        db.execSQL(SQL_CREATE_NIGHT_TABLE);
        db.execSQL(SQL_CREATE_DUNWICH_TABLE);
        db.execSQL(SQL_CREATE_CARCOSA_TABLE);
        db.execSQL(SQL_CREATE_FORGOTTEN_TABLE);
        db.execSQL(SQL_CREATE_CHAOS_BAG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                String SQL_UPGRADE_ONE_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_ZOEY_INUSE + " INTEGER";
                String SQL_UPGRADE_ONE_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_REX_INUSE + " INTEGER";
                String SQL_UPGRADE_ONE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_JENNY_INUSE + " INTEGER";
                String SQL_UPGRADE_ONE_FOUR = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_JIM_INUSE + " INTEGER";
                String SQL_UPGRADE_ONE_FIVE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_PETE_INUSE + " INTEGER";

                db.execSQL(SQL_UPGRADE_ONE_ONE);
                db.execSQL(SQL_UPGRADE_ONE_TWO);
                db.execSQL(SQL_UPGRADE_ONE_THREE);
                db.execSQL(SQL_UPGRADE_ONE_FOUR);
                db.execSQL(SQL_UPGRADE_ONE_FIVE);
            case 2:
                String SQL_CREATE_DUNWICH_TABLE = "CREATE TABLE " + DunwichEntry.TABLE_NAME + " ("
                        + DunwichEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + DunwichEntry.PARENT_ID + " INTEGER NOT NULL, "
                        + DunwichEntry.COLUMN_FIRST_SCENARIO + " INTEGER, "
                        + DunwichEntry.COLUMN_INVESTIGATORS_UNCONSCIOUS + " INTEGER, "
                        + DunwichEntry.COLUMN_HENRY_ARMITAGE + " INTEGER, "
                        + DunwichEntry.COLUMN_WARREN_RICE + " INTEGER, "
                        + DunwichEntry.COLUMN_STUDENTS + " INTEGER, "
                        + DunwichEntry.COLUMN_FRANCIS_MORGAN + " INTEGER, "
                        + DunwichEntry.COLUMN_OBANNION_GANG + " INTEGER, "
                        + DunwichEntry.COLUMN_INVESTIGATORS_CHEATED + " INTEGER);";
                db.execSQL(SQL_CREATE_DUNWICH_TABLE);
            case 3:
            case 4:
                String SQL_UPGRADE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_ROUGAROU_STATUS + " INTEGER";
                db.execSQL(SQL_UPGRADE_THREE);
            case 5:
                String SQL_UPGRADE_FOUR = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_STRANGE_SOLUTION + " INTEGER";
                db.execSQL(SQL_UPGRADE_FOUR);
            case 6:
                String SQL_UPGRADE_FIVE_ONE = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " + DunwichEntry
                        .COLUMN_NECRONOMICON + " INTEGER";
                String SQL_UPGRADE_FIVE_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CARNEVALE_STATUS + " INTEGER";
                String SQL_UPGRADE_FIVE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CARNEVALE_REWARDS + " INTEGER";
                db.execSQL(SQL_UPGRADE_FIVE_ONE);
                db.execSQL(SQL_UPGRADE_FIVE_TWO);
                db.execSQL(SQL_UPGRADE_FIVE_THREE);
            case 7:
                String SQL_UPGRADE_SIX_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_NIGHT_COMPLETED + " INTEGER";
                String SQL_UPGRADE_SIX_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_DUNWICH_COMPLETED + " INTEGER";
                String SQL_UPGRADE_SIX_THREE = "ALTER TABLE " + NightEntry.TABLE_NAME + " ADD COLUMN " +
                        NightEntry.COLUMN_UMORDHOTH_STATUS + " INTEGER";
                db.execSQL(SQL_UPGRADE_SIX_ONE);
                db.execSQL(SQL_UPGRADE_SIX_TWO);
                db.execSQL(SQL_UPGRADE_SIX_THREE);
            case 8:
                String SQL_UPGRADE_SEVEN_ONE = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_PLAYER + " STRING";
                String SQL_UPGRADE_SEVEN_TWO = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_DECKNAME + " STRING";
                String SQL_UPGRADE_SEVEN_THREE = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_DECKLIST + " STRING";
                String SQL_UPGRADE_SEVEN_FOUR = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " +
                        DunwichEntry.COLUMN_DELAYED + " INTEGER";
                db.execSQL(SQL_UPGRADE_SEVEN_ONE);
                db.execSQL(SQL_UPGRADE_SEVEN_TWO);
                db.execSQL(SQL_UPGRADE_SEVEN_THREE);
                db.execSQL(SQL_UPGRADE_SEVEN_FOUR);
            case 9:
                String SQL_UPGRADE_EIGHT_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry
                                .COLUMN_DIFFICULTY + " INTEGER";
                String SQL_UPGRADE_EIGHT_TWO = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " +
                        DunwichEntry.COLUMN_ADAM_LYNCH_HAROLD_WALSTED + " INTEGER";
                String SQL_UPGRADE_EIGHT_SIX = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " +
                        DunwichEntry.COLUMN_SILAS_BISHOP + " INTEGER";
                String SQL_UPGRADE_EIGHT_THREE = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " +
                        DunwichEntry.COLUMN_ZEBULON_WHATELEY + " INTEGER";
                String SQL_UPGRADE_EIGHT_FOUR = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " +
                        DunwichEntry.COLUMN_EARL_SAWYER + " INTEGER";
                String SQL_UPGRADE_EIGHT_FIVE = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " +
                        DunwichEntry.COLUMN_ALLY_SACRIFICED + " INTEGER";
                db.execSQL(SQL_UPGRADE_EIGHT_ONE);
                db.execSQL(SQL_UPGRADE_EIGHT_TWO);
                db.execSQL(SQL_UPGRADE_EIGHT_THREE);
                db.execSQL(SQL_UPGRADE_EIGHT_FOUR);
                db.execSQL(SQL_UPGRADE_EIGHT_FIVE);
                db.execSQL(SQL_UPGRADE_EIGHT_SIX);
            case 10:
                String SQL_UPGRADE_NINE_ONE = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " +
                        DunwichEntry.COLUMN_TOWNSFOLK + " INTEGER";
                String SQL_UPGRADE_NINE_TWO = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " +
                        DunwichEntry.COLUMN_BROOD_ESCAPED + " INTEGER";
                db.execSQL(SQL_UPGRADE_NINE_ONE);
                db.execSQL(SQL_UPGRADE_NINE_TWO);
            case 11:
                String SQL_UPGRADE_TEN = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " + DunwichEntry
                        .COLUMN_INVESTIGATORS_GATE + " INTEGER";
                db.execSQL(SQL_UPGRADE_TEN);
            case 12:
                String SQL_UPGRADE_ELEVEN = "ALTER TABLE " + DunwichEntry.TABLE_NAME + " ADD COLUMN " + DunwichEntry
                        .COLUMN_YOG_SOTHOTH + " INTEGER";
                db.execSQL(SQL_UPGRADE_ELEVEN);
            case 13:
                String SQL_UPGRADE_TWELVE_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_MARK_INUSE + " INTEGER";
                String SQL_UPGRADE_TWELVE_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_MINH_INUSE + " INTEGER";
                String SQL_UPGRADE_TWELVE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_SEFINA_INUSE + " INTEGER";
                String SQL_UPGRADE_TWELVE_FOUR = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_AKACHI_INUSE + " INTEGER";
                String SQL_UPGRADE_TWELVE_FIVE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_WILLIAM_INUSE + " INTEGER";
                String SQL_UPGRADE_TWELVE_SIX = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_LOLA_INUSE + " INTEGER";
                String SQL_UPGRADE_TWELVE_SEVEN = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_ARCHAIC_GLYPHS + " INTEGER";
                db.execSQL(SQL_UPGRADE_TWELVE_ONE);
                db.execSQL(SQL_UPGRADE_TWELVE_TWO);
                db.execSQL(SQL_UPGRADE_TWELVE_THREE);
                db.execSQL(SQL_UPGRADE_TWELVE_FOUR);
                db.execSQL(SQL_UPGRADE_TWELVE_FIVE);
                db.execSQL(SQL_UPGRADE_TWELVE_SIX);
                db.execSQL(SQL_UPGRADE_TWELVE_SEVEN);
            case 14:
                String SQL_CREATE_CARCOSA_TABLE = "CREATE TABLE " + CarcosaEntry.TABLE_NAME + " ("
                        + CarcosaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + CarcosaEntry.PARENT_ID + " INTEGER NOT NULL);";
                db.execSQL(SQL_CREATE_CARCOSA_TABLE);
            case 15:
                String SQL_UPGRADE_THIRTEEN_ONE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_DOUBT + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_TWO = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_CONVICTION + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_THREE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_CHASING_STRANGER + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_FOUR = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_STRANGER + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_FIVE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_POLICE + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_SIX = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_THEATRE + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_SEVEN = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_CONSTANCE + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_EIGHT = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_JORDAN + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_NINE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_ISHIMARU + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_TEN = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_SEBASTIEN + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_ELEVEN = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_ASHLEIGH + " INTEGER";
                String SQL_UPGRADE_THIRTEEN_TWELVE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_PARTY + " INTEGER";
                db.execSQL(SQL_UPGRADE_THIRTEEN_ONE);
                db.execSQL(SQL_UPGRADE_THIRTEEN_TWO);
                db.execSQL(SQL_UPGRADE_THIRTEEN_THREE);
                db.execSQL(SQL_UPGRADE_THIRTEEN_FOUR);
                db.execSQL(SQL_UPGRADE_THIRTEEN_FIVE);
                db.execSQL(SQL_UPGRADE_THIRTEEN_SIX);
                db.execSQL(SQL_UPGRADE_THIRTEEN_SEVEN);
                db.execSQL(SQL_UPGRADE_THIRTEEN_EIGHT);
                db.execSQL(SQL_UPGRADE_THIRTEEN_NINE);
                db.execSQL(SQL_UPGRADE_THIRTEEN_TEN);
                db.execSQL(SQL_UPGRADE_THIRTEEN_ELEVEN);
                db.execSQL(SQL_UPGRADE_THIRTEEN_TWELVE);
            case 16:
                String SQL_UPGRADE_FOURTEEN = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_ONYX + " INTEGER";
                db.execSQL(SQL_UPGRADE_FOURTEEN);
            case 17:
                String SQL_UPGRADE_FIFTEEN_ONE = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_SPENT_XP + " INTEGER NOT NULL DEFAULT 0";
                String SQL_UPGRADE_FIFTEEN_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CAMPAIGN_VERSION + " INTEGER NOT NULL DEFAULT 1";
                String SQL_UPGRADE_FIFTEEN_THREE = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_TOTAL_XP + " INTEGER NOT NULL DEFAULT 0";
                String SQL_UPGRADE_FIFTEEN_FOUR = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_MARIE_INUSE + " INTEGER";
                db.execSQL(SQL_UPGRADE_FIFTEEN_ONE);
                db.execSQL(SQL_UPGRADE_FIFTEEN_TWO);
                db.execSQL(SQL_UPGRADE_FIFTEEN_THREE);
                db.execSQL(SQL_UPGRADE_FIFTEEN_FOUR);
            case 18:
                String SQL_UPGRADE_SIXTEEN_ONE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_ASYLUM + " INTEGER";
                String SQL_UPGRADE_SIXTEEN_TWO = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_DANIEL + " INTEGER";
                String SQL_UPGRADE_SIXTEEN_THREE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_DANIELS_WARNING + " INTEGER";
                db.execSQL(SQL_UPGRADE_SIXTEEN_ONE);
                db.execSQL(SQL_UPGRADE_SIXTEEN_TWO);
                db.execSQL(SQL_UPGRADE_SIXTEEN_THREE);
            case 19:
                String SQL_UPGRADE_SEVENTEEN_ONE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_DREAMS + " INTEGER NOT NULL DEFAULT 0";
                String SQL_UPGRADE_SEVENTEEN_TWO = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_NIGEL + " INTEGER";
                db.execSQL(SQL_UPGRADE_SEVENTEEN_ONE);
                db.execSQL(SQL_UPGRADE_SEVENTEEN_TWO);
            case 20:
                String SQL_UPGRADE_EIGHTEEN = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_NORMAN_INUSE + " INTEGER";
                db.execSQL(SQL_UPGRADE_EIGHTEEN);
            case 21:
                String SQL_UPGRADE_NINETEEN = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CHAOS_BAG + " INTEGER NOT NULL DEFAULT -1";
                String SQL_CREATE_CHAOS_BAG_TABLE = "CREATE TABLE " + ChaosBagEntry.TABLE_NAME + " ("
                        + ChaosBagEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ChaosBagEntry.COLUMN_CHAOS_BAG_NAME + " STRING NOT NULL, "
                        + ChaosBagEntry.COLUMN_CAMPAIGN_TOKENS + " INTEGER NOT NULL, "
                        + ChaosBagEntry.COLUMN_ONE + " INTEGER, "
                        + ChaosBagEntry.COLUMN_TWO + " INTEGER, "
                        + ChaosBagEntry.COLUMN_THREE + " INTEGER, "
                        + ChaosBagEntry.COLUMN_FOUR + " INTEGER, "
                        + ChaosBagEntry.COLUMN_FIVE + " INTEGER, "
                        + ChaosBagEntry.COLUMN_SIX + " INTEGER, "
                        + ChaosBagEntry.COLUMN_SEVEN + " INTEGER, "
                        + ChaosBagEntry.COLUMN_EIGHT + " INTEGER, "
                        + ChaosBagEntry.COLUMN_NINE + " INTEGER, "
                        + ChaosBagEntry.COLUMN_TEN + " INTEGER, "
                        + ChaosBagEntry.COLUMN_ELEVEN + " INTEGER, "
                        + ChaosBagEntry.COLUMN_TWELVE + " INTEGER, "
                        + ChaosBagEntry.COLUMN_THIRTEEN + " INTEGER, "
                        + ChaosBagEntry.COLUMN_FOURTEEN + " INTEGER, "
                        + ChaosBagEntry.COLUMN_FIFTEEN + " INTEGER, "
                        + ChaosBagEntry.COLUMN_SIXTEEN + " INTEGER);";
                db.execSQL(SQL_UPGRADE_NINETEEN);
                db.execSQL(SQL_CREATE_CHAOS_BAG_TABLE);
            case 22:
                String SQL_UPGRADE_TWENTY_ONE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD COLUMN " +
                        CarcosaEntry.COLUMN_INV_ONE_READ_ACT + " INTEGER";
                String SQL_UPGRADE_TWENTY_TWO = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " +
                        CarcosaEntry.COLUMN_INV_TWO_READ_ACT + " INTEGER";
                String SQL_UPGRADE_TWENTY_THREE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " +
                        CarcosaEntry.COLUMN_INV_THREE_READ_ACT + " INTEGER";
                String SQL_UPGRADE_TWENTY_FOUR = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " +
                        CarcosaEntry.COLUMN_INV_FOUR_READ_ACT + " INTEGER";
                db.execSQL(SQL_UPGRADE_TWENTY_ONE);
                db.execSQL(SQL_UPGRADE_TWENTY_TWO);
                db.execSQL(SQL_UPGRADE_TWENTY_THREE);
                db.execSQL(SQL_UPGRADE_TWENTY_FOUR);
            case 23:
                String SQL_UPGRADE_TWENTYONE_A = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CARCOSA_COMPLETED + " INTEGER";
                String SQL_UPGRADE_TWENTYONE_B = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_PATH + " INTEGER";
                String SQL_UPGRADE_TWENTYONE_C = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_NOTES + " STRING";
                db.execSQL(SQL_UPGRADE_TWENTYONE_A);
                db.execSQL(SQL_UPGRADE_TWENTYONE_B);
                db.execSQL(SQL_UPGRADE_TWENTYONE_C);
            case 24:
                String SQL_UPGRADE_TWENTYTWO_ONE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " +
                        CarcosaEntry.COLUMN_INV_ONE_POSSESSED + " INTEGER";
                String SQL_UPGRADE_TWENTYTWO_TWO = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " +
                        CarcosaEntry.COLUMN_INV_TWO_POSSESSED + " INTEGER";
                String SQL_UPGRADE_TWENTYTWO_THREE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " +
                        CarcosaEntry.COLUMN_INV_THREE_POSSESSED + " INTEGER";
                String SQL_UPGRADE_TWENTYTWO_FOUR = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " +
                        CarcosaEntry.COLUMN_INV_FOUR_POSSESSED + " INTEGER";
                String SQL_UPGRADE_TWENTYTWO_FIVE = "ALTER TABLE " + CarcosaEntry.TABLE_NAME + " ADD " +
                        "COLUMN " + CarcosaEntry.COLUMN_HASTUR + " INTEGER";
                db.execSQL(SQL_UPGRADE_TWENTYTWO_ONE);
                db.execSQL(SQL_UPGRADE_TWENTYTWO_TWO);
                db.execSQL(SQL_UPGRADE_TWENTYTWO_THREE);
                db.execSQL(SQL_UPGRADE_TWENTYTWO_FOUR);
                db.execSQL(SQL_UPGRADE_TWENTYTWO_FIVE);
            case 25:
                String SQL_UPGRADE_TWENTYTHREE_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry
                                .COLUMN_CAROLYN_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYTHREE_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_SILAS_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYTHREE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_LEO_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYTHREE_FOUR = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_URSULA_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYTHREE_FIVE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_FINN_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYTHREE_SIX = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_MATEO_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYTHREE_SEVEN = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CALVIN_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYTHREE_EIGHT = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_CHARONS_OBOL + " INTEGER DEFAULT 0";
                db.execSQL(SQL_UPGRADE_TWENTYTHREE_ONE);
                db.execSQL(SQL_UPGRADE_TWENTYTHREE_TWO);
                db.execSQL(SQL_UPGRADE_TWENTYTHREE_THREE);
                db.execSQL(SQL_UPGRADE_TWENTYTHREE_FOUR);
                db.execSQL(SQL_UPGRADE_TWENTYTHREE_FIVE);
                db.execSQL(SQL_UPGRADE_TWENTYTHREE_SIX);
                db.execSQL(SQL_UPGRADE_TWENTYTHREE_SEVEN);
                db.execSQL(SQL_UPGRADE_TWENTYTHREE_EIGHT);
            case 26:
                String SQL_UPGRADE_TWENTYFOUR_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_ANCIENT_STONE + " INTEGER DEFAULT 0";
                String SQL_UPGRADE_TWENTYFOUR_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_DOOMED + " INTEGER DEFAULT 0";
                db.execSQL(SQL_UPGRADE_TWENTYFOUR_ONE);
                db.execSQL(SQL_UPGRADE_TWENTYFOUR_TWO);
            case 27:
                String SQL_UPGRADE_TWENTYFIVE_ONE = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_PROVISIONS + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_TWO = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_MEDICINE + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_THREE = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_SUPPLIES + " INTEGER";
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_ONE);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_TWO);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_THREE);
            case 28:
                Log.i("version", Integer.toString(oldVersion));
                String SQL_CREATE_FORGOTTEN_TABLE = "CREATE TABLE " + ForgottenEntry.TABLE_NAME + " ("
                        + ForgottenEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ForgottenEntry.PARENT_ID + " INTEGER NOT NULL);";
                db.execSQL(SQL_CREATE_FORGOTTEN_TABLE);
                String SQL_UPGRADE_TWENTYFIVE_FOUR = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_YIGS_FURY + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_FIVE = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_RUINS + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_SIX = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_ICHTACA + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_SEVEN = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_ALEJANDRO + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_EIGHT = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_LOW_RATIONS + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_NINE = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_RELIC + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_TEN = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_HARBINGER + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_ELEVEN = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_EZTLI + " INTEGER";
                String SQL_UPGRADE_TWENTYFIVE_TWELVE = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_CUSTODY + " INTEGER";
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_FOUR);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_FIVE);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_SIX);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_SEVEN);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_EIGHT);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_NINE);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_TEN);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_ELEVEN);
                db.execSQL(SQL_UPGRADE_TWENTYFIVE_TWELVE);
            case 29:
            case 30:
                String SQL_UPGRADE_TWENTYSIX_ONE = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_ICHTACAS_TALE + " INTEGER";
                String SQL_UPGRADE_TWENTYSIX_TWO = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_MISSING_RELIC + " INTEGER";
                String SQL_UPGRADE_TWENTYSIX_THREE = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_MISSING_ALEJANDRO + " INTEGER";
                String SQL_UPGRADE_TWENTYSIX_FOUR = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_MISSING_ICHTACA + " INTEGER";
                db.execSQL(SQL_UPGRADE_TWENTYSIX_ONE);
                db.execSQL(SQL_UPGRADE_TWENTYSIX_TWO);
                db.execSQL(SQL_UPGRADE_TWENTYSIX_THREE);
                db.execSQL(SQL_UPGRADE_TWENTYSIX_FOUR);
            case 31:
                String SQL_UPGRADE_TWENTYSEVEN_ONE = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_PATHS_KNOWN + " INTEGER";
                String SQL_UPGRADE_TWENTYSEVEN_TWO = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_ICHTACA_CONFIDENCE + " INTEGER";
                String SQL_UPGRADE_TWENTYSEVEN_THREE = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN "
                        + InvestigatorEntry.COLUMN_INVESTIGATOR_RESUPPLIES_ONE + " INTEGER";
                String SQL_UPGRADE_TWENTYSEVEN_FOUR = "ALTER TABLE " + ForgottenEntry.TABLE_NAME + " ADD COLUMN " +
                        ForgottenEntry.COLUMN_GASOLINE_USED + " INTEGER";
                db.execSQL(SQL_UPGRADE_TWENTYSEVEN_ONE);
                db.execSQL(SQL_UPGRADE_TWENTYSEVEN_TWO);
                db.execSQL(SQL_UPGRADE_TWENTYSEVEN_THREE);
                db.execSQL(SQL_UPGRADE_TWENTYSEVEN_FOUR);
            case 32:
                String SQL_UPGRADE_TWENTYEIGHT = "ALTER TABLE " + InvestigatorEntry.TABLE_NAME + " ADD COLUMN " +
                        InvestigatorEntry.COLUMN_INVESTIGATOR_RESUPPLIES_ONE + " INTEGER";
                try {
                    db.execSQL(SQL_UPGRADE_TWENTYEIGHT);
                } catch (SQLiteException ex) {
                    Log.w("SQLiteException", "Altering " + InvestigatorEntry.TABLE_NAME + ": " + ex.getMessage());
                }
            case 33:
                String SQL_UPGRADE_TWENTYNINE_ONE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_JOE_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYNINE_TWO = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_PRESTON_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYNINE_THREE = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_DIANA_INUSE + " INTEGER";
                String SQL_UPGRADE_TWENTYNINE_FOUR = "ALTER TABLE " + CampaignEntry.TABLE_NAME + " ADD COLUMN " +
                        CampaignEntry.COLUMN_RITA_INUSE + " INTEGER";
                db.execSQL(SQL_UPGRADE_TWENTYNINE_ONE);
                db.execSQL(SQL_UPGRADE_TWENTYNINE_TWO);
                db.execSQL(SQL_UPGRADE_TWENTYNINE_THREE);
                db.execSQL(SQL_UPGRADE_TWENTYNINE_FOUR);
        }
    }
}
