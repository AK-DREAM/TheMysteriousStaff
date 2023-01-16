package theWizard.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theWizard.DefaultMod;
import theWizard.characters.TheWizard;

import static theWizard.DefaultMod.makeCardPath;

public class FullyArmed extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(FullyArmed.class.getSimpleName());
    public static final String IMG = makeCardPath("FullyArmed.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheWizard.Enums.COLOR_GRAY;

    private static final int COST = 4;
    private static final int UPGRADED_COST = 4;
    private static final int MAGIC = 2;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLS_BLK = 4;
    private int MaxCost;

    // /STAT DECLARATION/


    public FullyArmed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = block = BLOCK; MaxCost = COST;
    }
    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.MaxCost = this.cost;
    }
    @Override
    public void applyPowers() {
        super.applyPowers();
        int Dec = (AbstractDungeon.player.hand.size()-1)/2;
        this.setCostForTurn(Math.max(0, Math.min(this.cost-Dec, this.MaxCost))+100);
    }
    @Override
    public void setCostForTurn(int amt) {
        if (amt >= 100) amt -= 100;
        else this.MaxCost = Math.min(this.MaxCost,amt);
        if (this.costForTurn >= 0) {
            this.costForTurn = amt;
            if (this.costForTurn < 0) {
                this.costForTurn = 0;
            }
            if (this.costForTurn != this.cost) {
                this.isCostModifiedForTurn = true;
            }
        }

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, block));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeBlock(UPGRADE_PLS_BLK);
            initializeDescription();
        }
    }
}
