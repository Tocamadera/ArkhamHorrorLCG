package com.whitdan.arkhamhorrorlcgcampaignguide.Z_Data;

public class Investigator {
    // Integer values for each investigator
    public static final int ROLAND_BANKS = 1;
    public static final int DAISY_WALKER = 2;
    public static final int SKIDS_OTOOLE = 3;
    public static final int AGNES_BAKER = 4;
    public static final int WENDY_ADAMS = 5;
    public static final int ZOEY_SAMARAS = 6;
    public static final int REX_MURPHY = 7;
    public static final int JENNY_BARNES = 8;
    public static final int JIM_CULVER = 9;
    public static final int ASHCAN_PETE = 10;
    public static final int MARK_HARRIGAN = 11;
    public static final int MINH_THI_PHAN = 12;
    public static final int SEFINA_ROUSSEAU = 13;
    public static final int AKACHI_ONYELE = 14;
    public static final int WILLIAM_YORICK = 15;
    public static final int LOLA_HAYES = 16;
    public static final int MARIE_LAMBEAU = 17;
    public static final int NORMAN_WITHERS = 18;
    public static final int CAROLYN_FERN = 19;
    public static final int SILAS_MARSH = 20;
    public static final int LEO_ANDERSON = 21;
    public static final int URSULA_DOWNS = 22;
    public static final int FINN_EDWARDS = 23;
    public static final int FATHER_MATEO = 24;
    public static final int CALVIN_WRIGHT = 25;
    public static final int JOE_DIAMOND = 26;
    public static final int PRESTON_FAIRMONT = 27;
    public static final int DIANA_STANLEY = 28;
    public static final int RITA_YOUNG = 29;
    public static final int GAVRIELLA_MIZRAH = 30;
    public static final int JEROME_DAVIDS = 31;
    public static final int PENNY_WHITE = 32;
    public static final int VALENTINO_RIVAS = 33;
    public static final int TOMMY_MULDOON = 34;
    public static final int MANDY_THOMPSON = 35;
    public static final int TONY_MORGAN = 36;
    public static final int LUKE_ROBINSON = 37;
    public static final int PATRICE_HATHAWAY = 38;

    // Sets maximum health and sanity values for the various investigators (correspond to the names in the string array)
    // StartingXP sets the bonus XP available at start for an investigator (for applicable investigators)
    private int[] health        = {0,9,5,8,6,7,9,6,8,7,6,9,7,5,6,8,6,6,6,6,9,8,7,7,6,6,8,7,7,9,8,4,7,5,8,6,9,5,7};
    private int[] sanity        = {0,5,9,6,8,7,6,9,7,8,5,5,7,9,8,6,6,8,8,9,5,6,7,7,8,6,6,7,7,5,4,8,5,7,6,8,5,9,7};
    private int[] startingXP    = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    public Investigator(int investigator, String name, String deckName, String deck){
        setupInvestigator(investigator);
        this.PlayerName = name;
        this.DeckName = deckName;
        this.Decklist = deck;
    }

    private void setupInvestigator(int investigator){
        this.Name = investigator;
        this.Health = health[Name];
        this.Sanity = sanity[Name];
        this.Status = 1;
        this.Damage = 0;
        this.Horror = 0;
        this.TotalXP = startingXP[Name];
        this.AvailableXP = this.TotalXP;
        this.SpentXP = 0;
        this.Supplies = 1;
        this.ResuppliesOne = 1;
    }

    // Basic attributes for all investigators
    public int Name;
    public int Health;
    public int Sanity;
    public int Status;          // 0 = not in use, 1 = in use, 2 = dead, 3 = saved
    public int Damage;
    public int Horror;
    public int TotalXP;
    public int AvailableXP;
    public int SpentXP;
    public String PlayerName;
    public String DeckName;
    public String Decklist;

    // Temp variables for when a change might be pending clicking the continue button
    public int TempStatus;      // 0 = normal, 1 = resigned, 2 = health, 3 = horror
    public int TempWeakness;    // 0 = not active, 1 = active
    public int TempWeaknessOne;
    public int TempWeaknessTwo;
    public int TempDamage;
    public int TempHorror;
    public int TempTotalXP;
    public int TempAvailableXP;

    // Supplies
    public int SupplyPoints;
    public int Provisions;
    public int Medicine;
    public int Gasoline;
    public int Supplies;
    public int ResuppliesOne;
}
