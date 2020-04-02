package pl.edu.agh.dronka.shop.model.filter;

import pl.edu.agh.dronka.shop.model.Category;
import pl.edu.agh.dronka.shop.model.ItemTypes.*;

public class ItemFilter {

	private Item itemSpec = new Item();

	public ItemFilter(Category category){
		switch (category) {
			case BOOKS:
				itemSpec=new Books();
				break;
			case ELECTRONICS:
				itemSpec=new Electronics();
				break;
			case FOOD:
				itemSpec=new Food();
				break;
			case MUSIC:
				itemSpec=new Music();
				break;
			case SPORT:
				itemSpec=new Sport();
				break;
			default:
				itemSpec=new Item();
		}
	}

	public Item getItemSpec() {
		return itemSpec;
	}
	public boolean appliesTo(Item item) {
		if (itemSpec.getName() != null
				&& !itemSpec.getName().equals(item.getName())) {
			return false;
		}
		if (itemSpec.getCategory() != null
				&& !itemSpec.getCategory().equals(item.getCategory())) {
			return false;
		}

		// applies filter only if the flag (secondHand) is true)
		if (itemSpec.isSecondhand() && !item.isSecondhand()) {
			return false;
		}

		// applies filter only if the flag (polish) is true)
		if (itemSpec.isPolish() && !item.isPolish()) {
			return false;
		}

		// applies filter only if the flag (isHard) is true)
		if ((itemSpec instanceof Books) && (item instanceof Books) &&  ((Books)itemSpec).isHard() && !((Books)item).isHard()) {
			return false;
		}
		if ((itemSpec instanceof Electronics) && (item instanceof Electronics) &&  ((Electronics)itemSpec).isMobile() && !((Electronics)item).isMobile()) {
			return false;
		}
		if ((itemSpec instanceof Electronics) && (item instanceof Electronics) &&  ((Electronics)itemSpec).isHasGuarantee() && !((Electronics)item).isHasGuarantee()) {
			return false;
		}
		return (!(itemSpec instanceof Music)) || (!(item instanceof Music)) || !((Music) itemSpec).isHasVideo() || ((Music) item).isHasVideo();
	}

}