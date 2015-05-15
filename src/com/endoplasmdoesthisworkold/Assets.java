package com.endoplasmdoesthisworkold;

import com.endoplasm.engine.AssetNode;
import com.endoplasm.engine.AssetNodeGroup;

public class Assets extends AssetNodeGroup{
	
	public Textures TEXTURES = new Textures(this);
	public Models MODELS = new Models(this);

	public Assets(AssetNode PARENT) {
		super(PARENT);
		TEXTURES = new Textures(this);
		INDEX = new AssetNode[]{
				TEXTURES,
				MODELS
		};
	}

}
