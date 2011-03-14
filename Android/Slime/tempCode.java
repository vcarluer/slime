/** draws a texture inside a rect */
    public void drawRepeatInRect(GL10 gl, CGRect rect) {
        // gl.glEnable(GL_TEXTURE_2D);

        loadTexture(gl);

        // float width = (float) mWidth * _maxS;
        // float height = (float) mHeight * _maxT;

	    //float vertices[] = {
        //   rect.origin.x, rect.origin.y, /*0.0f,*/
		//	rect.origin.x + rect.size.width,	rect.origin.y,	/*0.0f,*/
		//	rect.origin.x, rect.origin.y + rect.size.height, /*0.0f,*/
		//	rect.origin.x + rect.size.width, rect.origin.y + rect.size.height, /*0.0f*/
        //};
        
                
        float vertices[] = {
        		rect.origin.x + rect.size.width, rect.origin.y + rect.size.height,
        		rect.origin.x, rect.origin.y + rect.size.height,
        		rect.origin.x + rect.size.width, rect.origin.y,
        		rect.origin.x, rect.origin.y        		
        };
        
        mVertices.put(vertices);
        mVertices.position(0);
        
        /*float coorMaxT = rect.size.height / this.pixelsWide();
        float coorMaxS = rect.size.width / this.pixelsWide();
        float coordinates[] = {0.0f, coorMaxT,
        		coorMaxS, coorMaxT,
                0.0f, 0.0f,
                coorMaxS, 0.0f};*/
        float coordinates[] = {
        	(rect.origin.x + rect.size.width)/ this.pixelsWide(), (rect.origin.y + rect.size.height)/ this.pixelsWide(),
        	rect.origin.x/ this.pixelsWide(), (rect.origin.y + rect.size.height)/ this.pixelsWide(),
        	(rect.origin.x + rect.size.width)/ this.pixelsWide(), rect.origin.y/ this.pixelsWide(),
    		rect.origin.x/ this.pixelsWide(), rect.origin.y/ this.pixelsWide()    		
        };

        mCoordinates.put(coordinates);
        mCoordinates.position(0);

        gl.glBindTexture(GL_TEXTURE_2D, _name);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_DECAL);
        gl.glVertexPointer(2, GL_FLOAT, 0, mVertices);
        gl.glTexCoordPointer(2, GL_FLOAT, 0, mCoordinates);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 4);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        
       /* gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        gl.glBindTexture(GL_TEXTURE_2D, _name);

        gl.glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        gl.glTexParameterx(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        gl.glVertexPointer(2, GL_FLOAT, 0, mVertices);
        gl.glTexCoordPointer(2, GL_FLOAT, 0, mCoordinates);
        gl.glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        // Clear the vertex and color arrays
        gl.glDisableClientState(GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY);*/

        //gl.glDisable(GL_TEXTURE_2D);
    }

