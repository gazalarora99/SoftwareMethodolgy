/**
 * 
 * @authors Gazal Arora and Whitney Alderman
 *
 */
package view;

import javafx.scene.control.Label.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.Photo;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
/**
 * 
 * ThumbnailCell extends the ListCell class for a Photo
 * Helps put a Photo and its caption in a cell in the listview
 *
 */
public class ThumbnailCell extends ListCell<Photo> {

	ImageView iv = new ImageView();
	Label captionTitle = new Label();
	Label caption = new Label();
	AnchorPane ap = new AnchorPane();
	StackPane sp = new StackPane();
	

/**
 * Constructor to set the width, height of the Cell and of the the photo thumbnail in it
 */
	public ThumbnailCell() {
		super();

		iv.setFitWidth(50.0);
		iv.setPreserveRatio(true);

		StackPane.setAlignment(iv, Pos.CENTER);

		sp.getChildren().add(iv);

		sp.setPrefHeight(55.0);
		sp.setPrefWidth(45.0);

		AnchorPane.setLeftAnchor(sp, 0.0);

		AnchorPane.setLeftAnchor(captionTitle, 55.0);
		AnchorPane.setTopAnchor(captionTitle, 20.0);
		AnchorPane.setLeftAnchor(caption, 106.0);
		AnchorPane.setTopAnchor(caption, 20.0);

		captionTitle.setFont(Font.font("System", FontWeight.BOLD, 12));
		ap.getChildren().addAll(sp, captionTitle, caption);

		ap.setPrefHeight(55.0);

		setGraphic(ap);
	}

	/**
	 * Overrides the updateItem method in List Cell, sets the caption and the photo in the cell
	 * @param photo - photo who's caption we set in the Cell
	 * @param b - boolean to know if we need to update the Item or not
	 */
	@Override
	public void updateItem(Photo photo, boolean b) {
		super.updateItem(photo, b);
		setText(null);
		if (photo == null) {
			captionTitle.setText("");
			caption.setText("");
			iv.setImage(null);
		}
		if (photo != null) {
			captionTitle.setText("Caption: ");
			caption.setText(photo.getCaption());
			iv.setImage(photo.getImage());
		}
	}

}

