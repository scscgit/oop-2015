/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * Stefan Ciberaj, ZS 2015/2016
 * Technicka univerzita v Kosiciach, Fakulta elektrotechniky a informatiky
 * <p/>
 * Licencia: Volny softver, Open-Source GNU GPL v3+
 * Vseobecna verejna licencia. Program je dovolene volne sirit a upravovat.
 * Upraveny program / cast programu moze ktokolvek vyuzit ako na osobne,
 * tak aj komercne ucely, ale nemoze ho vydat s vlastnym copyrightom,
 * ktory nie je kompatibilny s GNU GPL v3+.
 * gnu.org/licenses/gpl-faq.html
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see < http://www.gnu.org/licenses/ >.
 */

package sk.tuke.yolkfolk.actors.characters;

import sk.tuke.gamelib2.Actor;
import sk.tuke.gamelib2.Animation;
import sk.tuke.gamelib2.Item;
import sk.tuke.gamelib2.Message;
import sk.tuke.yolkfolk.actors.AbstractActor;
import sk.tuke.yolkfolk.actors.Cursable;
import sk.tuke.yolkfolk.actors.CurseEvent;
import sk.tuke.yolkfolk.actors.Observer;
import sk.tuke.yolkfolk.collectables.Key;
import sk.tuke.yolkfolk.collectables.Potion;
import sk.tuke.yolkfolk.collectables.Ring;
import sk.tuke.yolkfolk.collectables.SimpleKey;

/**
 * Secret character.
 * <p/>
 * Created by Steve on 30.12.2015.
 */
public class Witch extends AbstractActor implements SpellCaster, Observer<Cursable>, Item
{
	/**
	 * Middleman, mediator or negotiator, ancient translations are very vague.
	 * This secret magic energy transmutes certain objects and creates a brand new object.
	 * <p/>
	 * Created by Steve on 31.12.2015.
	 */
	public class MagicMediator
	{
		private Potion potion;
		private Ring ring;
		private SpellCaster spellCaster;

		{
			this.potion = null;
			this.ring = null;
			this.spellCaster = null;
		}

		public MagicMediator()
		{
		}

		//Regsiters any Actor, dispatching it to the correct method. Returns true if state of World changed.
		public boolean registerActor(Actor actor)
		{
			if (actor instanceof Potion)
			{
				return registerPotion((Potion) actor);
			}
			else if (actor instanceof Ring)
			{
				return registerRing((Ring) actor);
			}
			if (actor instanceof SpellCaster)
			{
				return registerCaster((SpellCaster) actor);
			}
			return false;
		}

		//Regsiters first Potion
		public boolean registerPotion(Potion potion)
		{
			if (this.potion == null && potion != null)
			{
				this.potion = potion;
				//Potion is taken away to be consumed in the process
				this.potion.removeFromWorld();
				doSomeMagic();
				return true;
			}
			return false;
		}

		//Regsiters first Ring
		public boolean registerRing(Ring ring)
		{
			if (this.ring == null && ring != null)
			{
				this.ring = ring;
				return doSomeMagic();
			}
			return false;
		}

		//Regsiters first Caster
		public boolean registerCaster(SpellCaster spellCaster)
		{
			if (this.spellCaster == null && spellCaster != null)
			{
				this.spellCaster = spellCaster;
				return doSomeMagic();
			}
			return false;
		}

		//Does some actual magic, returns true if World gets modified
		private boolean doSomeMagic()
		{
			//If both items are present, together with the intent of a magic caster, magic can happen
			if (this.potion != null && this.ring != null && this.spellCaster != null)
			{
				//The potion is consumed in the process, but the ring remains in the hands of Player
				this.potion = null;

				//A brand new Key appears
				Key key = new SimpleKey();
				key.setPosition(this.spellCaster.getX(), this.spellCaster.getY());
				getWorld().addActor(key);
				return true;
			}
			return false;
		}
	}

	//Constants
	public static final String NAME = "Witch";

	//Variables
	private boolean invisible;

	//Animations
	private static Animation normalAnimation;

	//Objects
	//A cursed player she is eager to help
	private Cursable readyToHelp;
	private MagicMediator middleman;

	//Static initialization
	static
	{
		Witch.normalAnimation = new Animation("sprites/goodwitchcook.png", 64, 64);
		//Witch.normalAnimation = new Animation("sprites/witch.png", 48, 56);
	}

	//Initialization
	{
		this.invisible = true;
		this.readyToHelp = null;
	}

	public Witch()
	{
		super(Witch.NAME, "sprites/invisible.png", 48, 48);
		CurseEvent.register(this);
		this.middleman = new MagicMediator();
		this.middleman.registerCaster(this);
	}

	//If a cursed player comes near and she is yet invisible, she appears and says her prophcey
	private void appearNearPlayer()
	{
		if (this.invisible && this.readyToHelp != null)
		{
			for (Actor actor : getWorld())
			{
				if (actor instanceof Cursable && actor.equals(this.readyToHelp) && actor.intersects(this))
				{
					new Message("To help or not to help.", "He, who brings me elixir of youth\n" +
					                                       "and one piece of quartz mineral\n" +
					                                       "shall receive path to home and\n" +
					                                       "loses nothing in the end.", this);
					setAnimationNormal(Witch.normalAnimation);
					setAnimation();
					this.invisible = false;
					break;
				}
			}
		}
	}

	@Override
	public void act()
	{
		appearNearPlayer();

		//When the witch is finally visible, she tries to do her magic
		if (!this.invisible)
		{
			for (Actor actor : getWorld())
			{
				//Offers the mediator in the other dimension (that's how this magic works) sacrifical items
				if (actor != this && actor.intersects(this) && this.middleman.registerActor(actor))
				{
					break;
				}

				/*
				if(actor instanceof Potion)
				{
					this.middleman.registerPotion((Potion)actor);
				}
				else if(actor instanceof Ring)
				{
					this.middleman.registerRing((Ring)actor);
				}
				*/
			}
		}
	}

	//Witch does not use the mana potion until the spell needs to be cast
	@Override
	public boolean boostMana()
	{
		return false;

		/*
		if (!this.giftedMana)
		{
			this.giftedMana = true;
			return true;
		}
		return false;
		*/
	}

	//Ak je hrac prekliaty, bosorka hned vie.
	@Override
	public void notified(Cursable notificator)
	{
		if (this.readyToHelp == null)
		{
			this.readyToHelp = notificator;
		}
	}
}
