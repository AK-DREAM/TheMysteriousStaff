package theWizard.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.sun.tools.javac.comp.Check;
import theWizard.DefaultMod;
import theWizard.actions.CheckManaCostAction;
import theWizard.characters.TheWizard;
import theWizard.powers.LightningStaffPower;

import static theWizard.DefaultMod.makeCardPath;

public class EnchantedBook extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(EnchantedBook.class.getSimpleName());
    public static final String IMG = makeCardPath("EnchantedBook.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.POWER;       //
    public static final CardColor COLOR = TheWizard.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;
    private static final int MANA_COST = 3;
    // /STAT DECLARATION/


    public EnchantedBook() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (CheckManaCostAction.CheckManaCost(MANA_COST)) {
            this.addToBot(
                    new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
            this.addToBot(
                    new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
            return;
        }
        int k = AbstractDungeon.player.exhaustPile.size();
        if (k % 2 == 0) {
            this.addToBot(
                    new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
        } else {
            this.addToBot(
                    new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
