package theWizard.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;
import theWizard.DefaultMod;
import theWizard.actions.AddManaAction;
import theWizard.actions.CheckManaCostAction;
import theWizard.characters.TheWizard;
import theWizard.powers.ColdPower;

import java.util.Iterator;

import static theWizard.DefaultMod.makeCardPath;

public class ColdWar extends AbstractDynamicCard {
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ColdWar.class.getSimpleName());
    public static final String IMG = makeCardPath("ColdWar.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheWizard.Enums.COLOR_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;
    private static final int MAGIC = 7;
    private static final int UPGRADE_MAGIC = 3;
    private static final int MANA_COST = 1;
    // /STAT DECLARATION/


    public ColdWar() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }
/*
    public boolean NoAttack() {
        Iterator it = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        boolean ok = true;
        while (it.hasNext()) {
            AbstractCard c = (AbstractCard)it.next();
            if (c.type == CardType.ATTACK) ok = false;
        }
        return ok;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (NoAttack()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean cc = super.canUse(p, m);
        if (!cc) {
            return false;
        } else if (!NoAttack()) {
            this.cantUseMessage = UPGRADE_DESCRIPTION;
            return false;
        } else return true;
    }
*/
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new ColdPower(m, p, magicNumber)));
        // AddManaAction.AddManaAction(2, false);
        if (CheckManaCostAction.CheckManaCost(MANA_COST)) {
            this.addToBot(new GainEnergyAction(1));
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
