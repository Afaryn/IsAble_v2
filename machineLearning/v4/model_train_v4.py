import os
import tensorflow as tf
import matplotlib.pyplot as plt
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense
from tensorflow.keras.metrics import Precision, Recall
from callback import MyCallback
import tensorflow.lite as lite

# Function to create and train the model
def training_model(train_dir, valid_dir, test_dir):
    """
    Trains a Convolutional Neural Network for sign language recognition.
    Args:
        train_dir (str): Directory path for training data.
        valid_dir (str): Directory path for validation data.
        test_dir (str): Directory path for test data.

    Returns:
        Sequential model: Trained Keras Sequential model.
    """

    # Create ImageDataGenerators for data augmentation and normalization
    train_image_datagen = ImageDataGenerator(
        rescale=1./255.,
        zoom_range=0.1,
        horizontal_flip=True
    )

    valid_image_datagen = ImageDataGenerator(
        rescale=1./255.
    )

    test_image_datagen = ImageDataGenerator(
        rescale=1./255.
    )

    # Create data generators for train, validation, and test datasets
    train_generator = train_image_datagen.flow_from_directory(
        train_dir,
        target_size=(244, 244),
        batch_size=32,
        class_mode='categorical'
    )

    # Make a validation generator
    valid_generator = valid_image_datagen.flow_from_directory(
        valid_dir,
        target_size=(244, 244),
        batch_size=32,
        class_mode='categorical'
    )

    # Make a test generator
    test_generator = test_image_datagen.flow_from_directory(
        test_dir,
        target_size=(244, 244),
        batch_size=32,
        class_mode='categorical'
    )

    # Build a model architecture and define them
    model = tf.keras.models.Sequential([
        tf.keras.layers.Conv2D(64, (3, 3), activation='relu', input_shape=(244, 244, 3)),
        tf.keras.layers.MaxPooling2D(2, 2),
        tf.keras.layers.Conv2D(64, (3, 3), activation='relu'),
        tf.keras.layers.MaxPooling2D(2, 2),
        tf.keras.layers.Conv2D(64, (3, 3), activation='relu'),
        tf.keras.layers.MaxPooling2D(2, 2),
        tf.keras.layers.Conv2D(128, (3, 3), activation='relu'),
        tf.keras.layers.MaxPooling2D(2, 2),
        tf.keras.layers.Flatten(),
        tf.keras.layers.Dropout(0.5),
        tf.keras.layers.Dense(512, activation='relu'),
        tf.keras.layers.Dense(26, activation='softmax')
    ])

    # Compile the model
    model.compile(
        loss='categorical_crossentropy',
        optimizer='adam',
        metrics=['accuracy', Precision(), Recall()]
    )

    # Initiialize custom callback
    callback = MyCallback()

    # Train the model
    history = model.fit(
        train_generator,
        validation_data=valid_generator,
        epochs=200,
        verbose=1,
        callbacks=callback
    )

    # Evaluate the model on the test set
    test_loss, test_accuracy, test_precision, test_recall = model.evaluate(test_generator)
    print(f'Test Accuracy: {test_accuracy}')
    print(f'Test Precision: {test_precision}')
    print(f'Test Recall: {test_recall}')

    # Plot and save training history including test set metrics
    plot_and_save_history(history, test_loss, test_accuracy, test_precision, test_recall, "training_and_test_history")

    # Save the model in H5 format
    model.save("sign_language_model_v4_rgb.h5")

    # Convert the model to TensorFlow Lite format
    converter = lite.TFLiteConverter.from_keras_model(model)
    tflite_model = converter.convert()

    # Save the TensorFlow Lite model to a file
    with open("sign_language_model_v4_rgb.tflite", "wb") as f:
        f.write(tflite_model)

    return model

# Function to plot history of training
def plot_and_save_history(history, test_loss, test_accuracy, test_precision, test_recall, filename):
    """
    Plots and saves the training history including accuracy, loss for training, validation,
    and test datasets.

    Args:
        history: Training history object from model.fit().
        test_loss (float): Loss on the test dataset.
        test_accuracy (float): Accuracy on the test dataset.
        test_precision (float): Precision on the test dataset.
        test_recall (float): Recall on the test dataset.
        filename (str): Filename for saving the plot.
    """

    # Plot training & validation accuracy values
    plt.figure(figsize=(12, 4))
    plt.subplot(1, 2, 1)
    plt.plot(history.history['accuracy'], label='Train')
    plt.plot(history.history['val_accuracy'], label='Validation')
    plt.axhline(y=test_accuracy, color='r', linestyle='-', label='Test Accuracy')
    plt.title('Model Accuracy')
    plt.ylabel('Accuracy')
    plt.xlabel('Epoch')
    plt.legend(loc='upper left')

    # Plot training & validation loss values
    plt.subplot(1, 2, 2)
    plt.plot(history.history['loss'], label='Train')
    plt.plot(history.history['val_loss'], label='Validation')
    plt.axhline(y=test_loss, color='r', linestyle='-', label='Test Loss')
    plt.title('Model Loss')
    plt.ylabel('Loss')
    plt.xlabel('Epoch')
    plt.legend(loc='upper left')

    plt.tight_layout()
    plt.savefig(filename + '.png')
    plt.show()

if __name__ == '__main__':
    train_dir = r'C:\Users\ramad\Downloads\sign_language\bisindo_large_dataset\train'
    valid_dir = r'C:\Users\ramad\Downloads\sign_language\bisindo_large_dataset\valid'
    test_dir = r'C:\Users\ramad\Downloads\sign_language\bisindo_large_dataset\test'
    model = training_model(train_dir, valid_dir, test_dir)
