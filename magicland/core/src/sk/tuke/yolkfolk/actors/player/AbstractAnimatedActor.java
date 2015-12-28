/***********************************************************
 * Zadanie na predmet Objektove Programovanie
 * <p/>
 * scsc
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

package sk.tuke.yolkfolk.actors.player;

import sk.tuke.gamelib2.Animation;
import sk.tuke.yolkfolk.actors.AbstractActor;

/**
 * Implementacia podpory roznorodych animacii pohybu, na ktore ma pravo kazdy actor, pomocou verejnych metod.
 * <p/>
 * Created by Steve on 11.12.2015.
 */
public abstract class AbstractAnimatedActor extends AbstractActor implements AnimatedMovement
{
	//Animations
	private Animation aniLeft, aniRight, aniJump, aniJumpLeft, aniJumpRight;
	private boolean isAnimationLeft, isAnimationRight, isAnimationJump;

	public AbstractAnimatedActor(String name, String animationString, int animationX, int animationY)
	{
		super(name, animationString, animationX, animationY);

		//Default animacie su rovnake ako zakladna animacia, odporuca sa nahradit ich v konstruktoroch
		setAnimationLeft(getAnimation());
		setAnimationRight(getAnimation());
		setAnimationJump(getAnimation());
		setAnimationJumpLeft(getAnimation());
		setAnimationJumpRight(getAnimation());

		this.isAnimationLeft = false;
		this.isAnimationRight = false;
		this.isAnimationJump = false;
	}

	public final void setAnimationLeft(Animation aniLeft)
	{
		this.aniLeft = aniLeft;
	}
	public final void setAnimationRight(Animation aniRight)
	{
		this.aniRight = aniRight;
	}
	public final void setAnimationJump(Animation aniJump)
	{
		this.aniJump = aniJump;
	}
	public final void setAnimationJumpLeft(Animation aniJumpLeft)
	{
		this.aniJumpLeft = aniJumpLeft;
	}
	public final void setAnimationJumpRight(Animation aniJumpRight)
	{
		this.aniJumpRight = aniJumpRight;
	}

	public final void runAnimationLeft()
	{
		this.isAnimationLeft = true;
		updateAnimation();
	}
	public final void runAnimationRight()
	{
		this.isAnimationRight = true;
		updateAnimation();
	}
	public final void runAnimationJump()
	{
		this.isAnimationJump = true;
		updateAnimation();
	}

	public final void stopAnimationLeft()
	{
		this.isAnimationLeft = false;
		updateAnimation();
	}
	public final void stopAnimationRight()
	{
		this.isAnimationRight = false;
		updateAnimation();
	}
	public final void stopAnimationJump()
	{
		this.isAnimationJump = false;
		updateAnimation();
	}

	private void updateAnimation()
	{
		if (this.isAnimationJump)
		{
			if (this.isAnimationLeft == this.isAnimationRight)
			{
				//Only jump
				setAnimation(this.aniJump);
			}
			else if (this.isAnimationLeft)
			{
				//Jump left
				setAnimation(this.aniJumpLeft);
			}
			else /*if (this.isAnimationRight)*/
			{
				//Jump right
				setAnimation(this.aniJumpRight);
			}
		}
		else if (this.isAnimationLeft == this.isAnimationRight)
		{
			//Default animation
			setAnimation();
		}
		else if (this.isAnimationLeft)
		{
			//Move left
			setAnimation(this.aniLeft);
		}
		else /*if(this.isAnimationRight)*/
		{
			//Move right
			setAnimation(this.aniRight);
		}
	}
}
