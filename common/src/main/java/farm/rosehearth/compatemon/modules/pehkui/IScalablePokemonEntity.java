package farm.rosehearth.compatemon.modules.pehkui;

/**
 * This was an attempt to fix hitboxes. No longer using it but it's serving as a reference
 * for how to use interfaces and mixins
 */
public interface IScalablePokemonEntity {
	
	abstract float compatemon$getSizeScale();
	
	abstract void compatemon$setSizeScale(float sizeScale);
}
