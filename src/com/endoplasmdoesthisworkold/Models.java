package com.endoplasmdoesthisworkold;

import com.endoplasm.engine.AssetNode;
import com.endoplasm.engine.AssetNodeGroup;
import com.endoplasm.engine.ModelAsset;

public class Models extends AssetNodeGroup{
	
	public ModelAsset BLOCKBASE = new ModelAsset(this, "/Resources/Models/blockbase.obj");

	public Models(AssetNode PARENT) {
		super(PARENT);
		INDEX = new AssetNode[]{
			BLOCKBASE
		};
	}

}
