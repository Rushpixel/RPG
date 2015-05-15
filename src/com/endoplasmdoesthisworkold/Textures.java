package com.endoplasmdoesthisworkold;

import com.endoplasm.engine.AssetNode;
import com.endoplasm.engine.AssetNodeGroup;
import com.endoplasm.engine.TextureAsset;

public class Textures extends AssetNodeGroup{
	
	public TextureAsset C1 = new TextureAsset(this, "/Resources/Textures/character1");
	public TextureAsset BLOCKBASE = new TextureAsset(this, "/Resources/Textures/blockbase");

	public Textures(AssetNode PARENT) {
		super(PARENT);
		INDEX = new AssetNode[]{
				C1,
				BLOCKBASE
		};
	}

}
