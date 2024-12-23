package model;


import java.util.*;

import math.Vector2f;
import math.Vector3f;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model model)) return false;
        return Objects.equals(vertices, model.vertices) && Objects.equals(textureVertices, model.textureVertices) && Objects.equals(normals, model.normals) && Objects.equals(polygons, model.polygons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, textureVertices, normals, polygons);
    }

    public static void removeVertices(Model model, List<Integer> verticesToRemove) {

        List<Polygon> polygonsToRemove = new ArrayList<>();
        for (Polygon polygon : model.polygons) {
            if (containsAny(polygon.getVertexIndices(), verticesToRemove)) {
                polygonsToRemove.add(polygon);
            }

        }
        model.polygons.removeAll(polygonsToRemove);

        Collections.sort(verticesToRemove, Collections.reverseOrder());
        for (int index : verticesToRemove) {
            if (index >= 0 && index < model.vertices.size()) {
                model.vertices.remove(index);
            }
        }
        for (Polygon polygon : model.polygons) {
            List<Integer> updatedIndices = new ArrayList<>();
            for (int vertexIndex : polygon.getVertexIndices()) {
                int offset = 0;
                for (int removedIndex : verticesToRemove) {
                    if (removedIndex < vertexIndex) {
                        offset++;
                    }
                }
                updatedIndices.add(vertexIndex - offset);
            }
            polygon.setVertexIndices(new ArrayList<>(updatedIndices));
        }
    }

    private static boolean containsAny(ArrayList<Integer> indices, List<Integer> verticesToRemove) {
        for (int index : indices) {
            if (verticesToRemove.contains(index)) {
                return true;
            }
        }
        return false;
    }
}

