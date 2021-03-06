/*
 * PolyLine3D.java
 *
 * Copyright (c) 2013-2018, F(X)yz
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of F(X)yz, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL F(X)yz BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.example.systemesolaire.utilitaires;

import javafx.scene.AmbientLight;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.util.List;

/**
 *
 * @author Sean
 */

public class PolyLine3D extends Group {

    public List<Vecteur3> points;
    public float width = 2.0f;
    public Color color = Color.WHITE;
    private TriangleMesh mesh;
    public MeshView meshView;
    public PhongMaterial material;
    public static enum LineType {RIBBON, TRIANGLE};

    public PolyLine3D(List<Vecteur3> points, float width, Color color, LineType lineType) {
        this.points = points;
        this.width = width;
        this.color = color;
        setDepthTest(DepthTest.ENABLE);
        mesh  = new TriangleMesh();
        switch(lineType) {
            case TRIANGLE: buildTriangleTube(); break;
            case RIBBON:
            default: buildRibbon(); break;
        }
        //Need to add the mesh to a MeshView before adding to our 3D scene
        meshView = new MeshView(mesh);
        meshView.setDrawMode(DrawMode.FILL);  //Fill so that the line shows width
        material = new PhongMaterial(color);
        material.setDiffuseColor(color);
        material.setSpecularColor(color);
        meshView.setMaterial(material);
        //Make sure you Cull the Back so that no black shows through
        meshView.setCullFace(CullFace.BACK);

        //Add some ambient light so folks can see it
        AmbientLight light = new AmbientLight(Color.WHITE);
        light.getScope().add(meshView);
        getChildren().add(light);
        getChildren().add(meshView);
    }
    private void buildTriangleTube() {
        //For each data point add three mesh points as an equilateral triangle
        float half = (float) (width / 2.0);
        for(Vecteur3 point : points) {
            //-0.288675f*hw, -0.5f*hw, -0.204124f*hw,
            mesh.getPoints().addAll((float)point.getX() - 0.288675f*half, (float)point.getY() - 0.5f*half, (float)point.getZ() - 0.204124f*half);
            //-0.288675f*hw, 0.5f*hw, -0.204124f*hw,
            mesh.getPoints().addAll((float)point.getX() - 0.288675f*half, (float)point.getY() + 0.5f*half, (float)point.getZ() - 0.204124f*half);
            //0.57735f*hw, 0f, -0.204124f*hw
            mesh.getPoints().addAll((float)point.getX() + 0.57735f*half, (float)point.getY() + 0.5f*half, (float)point.getZ() - 0.204124f*half);
        }
        //add dummy Texture Coordinate
        mesh.getTexCoords().addAll(0,0);
        //Beginning End Cap
        mesh.getFaces().addAll(0,0, 1,0, 2,0);
        //Now generate trianglestrips between each point
        for(int i=3;i<points.size()*3;i+=3) {  //add each triangle tube segment
            //Vertices wound counter-clockwise which is the default front face of any Triange
            //Triangle Tube Face 1
            mesh.getFaces().addAll(i+2,0, i-2,0, i+1,0); //add secondary Width face
            mesh.getFaces().addAll(i+2,0, i-1,0, i-2,0); //add primary face
            //Triangle Tube Face 2
            mesh.getFaces().addAll(i+2,0, i-3,0, i-1,0); //add secondary Width face
            mesh.getFaces().addAll(i,0, i-3,0, i+2,0); //add primary face
            //Triangle Tube Face 3
            mesh.getFaces().addAll(i,0, i+1,0, i-3,0); //add primary face
            mesh.getFaces().addAll(i+1,0, i-2,0, i-3,0); //add secondary Width face
        }
        //Final End Cap
        int last = points.size()*3 -1;
        mesh.getFaces().addAll(last,0, last-1,0, last-2,0);
    }
    private void buildRibbon() {
        //add each point. For each point add another point shifted on Z axis by width
        //This extra point allows us to build triangles later
        for(Vecteur3 point: points) {
            mesh.getPoints().addAll((float)point.getX(), (float)point.getY(), (float)point.getZ());
            mesh.getPoints().addAll((float)point.getX(), (float)point.getY(), (float)point.getZ() + width);
        }
        //add dummy Texture Coordinate
        mesh.getTexCoords().addAll(0,0);
        //Now generate trianglestrips for each line segment
        for(int i=2;i<points.size()*2;i+=2) {  //add each segment
            //Vertices wound counter-clockwise which is the default front face of any Triange
            //These triangles live on the frontside of the line facing the camera
            mesh.getFaces().addAll(i,0,i-2,0,i+1,0); //add primary face
            mesh.getFaces().addAll(i+1,0,i-2,0,i-1,0); //add secondary Width face
            //Add the same faces but wind them clockwise so that the color looks correct when camera is rotated
            //These triangles live on the backside of the line facing away from initial the camera
            mesh.getFaces().addAll(i+1,0,i-2,0,i,0); //add primary face
            mesh.getFaces().addAll(i-1,0,i-2,0,i+1,0); //add secondary Width face
        }
    }
}