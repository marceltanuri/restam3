package io.github.marceltanuri.frameworks.restam3.json;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * Interface for serialization (object -> JSON) and deserialization (JSON -> object)
 * that can be implemented using libraries like Gson or Jackson.
 */
public interface JsonParser {

    /**
     * Deserializes a JSON string to an object of the specified type.
     *
     * @param <T> The type of the object to be returned.
     * @param json The input JSON string.
     * @param typeOfT The target object's type. Use {@code Class<T>} for simple objects.
     * For generic or collection types, a Type reference may be necessary (e.g.,
     * Gson's {@code new TypeToken<List<YourClass>>(){}.getType()}).
     * @return The deserialized object of type T.
     * @throws Exception If an error occurs during deserialization (e.g., JsonSyntaxException, IOException).
     */
    <T> T fromJson(String json, Type typeOfT) throws Exception;

    /**
     * Deserializes from a reader (stream) to an object of the specified type.
     * Useful for handling large JSON files or streams efficiently.
     *
     * @param <T> The type of the object to be returned.
     * @param reader The reader (Reader) from which to read the JSON string.
     * @param typeOfT The target object's type.
     * @return The deserialized object of type T.
     * @throws Exception If an error occurs during deserialization.
     */
    <T> T fromJson(Reader reader, Type typeOfT) throws Exception;

    /**
     * Serializes an object to its JSON string representation.
     *
     * @param src The object to be serialized.
     * @return A string containing the JSON representation of the object.
     * @throws Exception If an error occurs during serialization (e.g., IOException).
     */
    String toJson(Object src) throws Exception;

    /**
     * Serializes an object to its JSON representation and writes it to an Appendable.
     * Useful for efficient writing to files or streams.
     *
     * @param src The object to be serialized.
     * @param writer The Appendable (like a Writer) where the JSON will be written.
     * @throws Exception If an error occurs during serialization.
     */
    void toJson(Object src, Appendable writer) throws Exception;
}