package theWizard.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theWizard.DefaultMod;
import theWizard.actions.CheckManaCostAction;
import theWizard.characters.TheWizard;

import static theWizard.DefaultMod.makeCardPath;

public class Shield extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Shield.class.getSimpleName());
    public static final String IMG = makeCardPath("Shield.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;

    private static final int BLOCK_AMOUNT = 2;
    private static final int UPGRADE_PLUS_BLOCK = 1;
    private static final int BLOCK_DIFFERENCE = 4;
    private static final int UPGRADE_PLUS_BLOCK_DIFFERENCE = 1;
    private static final int MANA_COST = 1;
    // /STAT DECLARATION/


    public Shield() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = BLOCK_AMOUNT;
        this.magicNumber = this.baseMagicNumber = BLOCK_AMOUNT+BLOCK_DIFFERENCE;
        this.exhaust = this.selfRetain = true;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.applyPowers();
        if (CheckManaCostAction.CheckManaCost(MANA_COST)) {
            this.addToBot(new GainBlockAction(p, p, block+magicNumber));
        } else {
            this.addToBot(new GainBlockAction(p, p, block));
        }
    }

    @Override
    public void applyPowers() {
        this.baseBlock += BLOCK_DIFFERENCE + this.timesUpgraded * UPGRADE_PLUS_BLOCK_DIFFERENCE;
        this.baseMagicNumber = this.baseBlock;
        super.applyPowers();
        this.magicNumber = this.block;
        this.isMagicNumberModified = this.isBlockModified;
        this.baseBlock -= BLOCK_DIFFERENCE + this.timesUpgraded * UPGRADE_PLUS_BLOCK_DIFFERENCE;
        super.applyPowers();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(this.UPGRADE_PLUS_BLOCK);
            this.upgradeMagicNumber(this.UPGRADE_PLUS_BLOCK+this.UPGRADE_PLUS_BLOCK_DIFFERENCE);
        }
    }
}
