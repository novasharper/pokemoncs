package finalgame.Graphics;

public class RenderPointer implements RenderObj {
	private RenderObj pointer;
	
	public RenderPointer() {}
	public RenderPointer(RenderObj p) {
		pointer = p;
	}
	
	public void setPointer(RenderObj p) {
		pointer = p;
	}
	
	public void render() {
		if(pointer != null)
			pointer.render();
	}

}
