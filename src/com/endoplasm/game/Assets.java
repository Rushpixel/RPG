package com.endoplasm.game;

import com.endoplasm.engine.AssetNode;
import com.endoplasm.engine.AssetNodeGroup;
import com.endoplasm.engine.TextureAsset;

	public class Assets extends AssetNodeGroup{
		
		public TextureAsset CHARACTERSHEET = new TextureAsset(this, "/Resources/Textures/charactersheet");

		public Assets(AssetNode PARENT) {
			super(PARENT);
			INDEX = new AssetNode[]{
					CHARACTERSHEET
			};
		}

}
