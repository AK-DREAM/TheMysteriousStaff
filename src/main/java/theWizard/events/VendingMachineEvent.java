package theWizard.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import theWizard.DefaultMod;

import java.util.ArrayList;
import java.util.List;

import static theWizard.DefaultMod.makeEventPath;

public class VendingMachineEvent extends AbstractImageEvent {

    public static final String ID = DefaultMod.makeID("VendingMachineEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);

    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("VendingMachineEvent.png");

    private int screenNum = 0; // The initial screen we will see when encountering the event - screen 0;

    public VendingMachineEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);

        // The first dialogue options available to us.
        int gg = AbstractDungeon.player.gold;
        imageEventText.setDialogOption(OPTIONS[0], gg<120);
        imageEventText.setDialogOption(OPTIONS[1], gg<75);
        imageEventText.setDialogOption(OPTIONS[2], gg<15);
        imageEventText.setDialogOption(OPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int i) { // This is the event:
        switch (screenNum) {
            case 0: // While you are on screen number 0 (The starting screen)
                switch (i) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        List<AbstractCard> C = new ArrayList();
                        C.add(AbstractDungeon.getCard(AbstractCard.CardRarity.COMMON));
                        C.add(AbstractDungeon.getCard(AbstractCard.CardRarity.UNCOMMON));
                        C.add(AbstractDungeon.getCard(AbstractCard.CardRarity.RARE));
                        for (AbstractCard c : C) {
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                        }
                        AbstractDungeon.player.loseGold(120);
                        break; // Onto screen 1 we go.
                    case 1: // If you press button the second button (Button at index 1), in this case: Denial
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        AbstractRelic relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                        AbstractDungeon.player.loseGold(75);
                        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relic);
                        break; // Onto screen 1 we go.
                    case 2: // If you press button the third button (Button at index 2), in this case: Acceptance
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;

                        if (AbstractDungeon.player.hasRelic("Sozu")) {
                            AbstractDungeon.player.getRelic("Sozu").flash();
                        } else {
                            AbstractPotion p = PotionHelper.getRandomPotion();
                            AbstractDungeon.player.obtainPotion(p);
                        }
                        AbstractDungeon.player.loseGold(15);
                        break;
                    case 3: // If you press button the fourth button (Button at index 3), in this case: TOUCH
                        this.imageEventText.updateBodyText(DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[4]);
                        this.imageEventText.clearRemainingOptions();
                        screenNum = 1;
                        break;
                }
                break;
            case 1: // Welcome to screenNum = 1;
                switch (i) {
                    case 0: // If you press the first (and this should be the only) button,
                        openMap(); // You'll open the map and end the event.
                        break;
                }
                break;
        }
    }
}
