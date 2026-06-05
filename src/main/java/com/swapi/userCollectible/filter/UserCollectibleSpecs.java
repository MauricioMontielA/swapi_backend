package com.swapi.userCollectible.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.swapi.collectibleItem.CollectibleItem;
import com.swapi.userCollectible.UserCollectible;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class UserCollectibleSpecs {
	
	public static Specification<UserCollectible> withFilter(UserCollectibleFilter filter) {
        return (root, query, cb) -> {
            root.fetch("collectibleItem", JoinType.INNER);

            Join<UserCollectible, CollectibleItem> item =
                    root.join("collectibleItem", JoinType.INNER);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getUserId() != null) {
                predicates.add(cb.equal(root.get("user").get("id"), filter.getUserId()));
            }

            if (filter.getCollectionId() != null) {
                predicates.add(cb.equal(item.get("collection").get("id"), filter.getCollectionId()));
            }

            if (filter.getRarity() != null) {
                predicates.add(cb.equal(item.get("rarity"), filter.getRarity()));
            }

            if (filter.getForTrade() != null) {
                predicates.add(cb.equal(root.get("isForTrade"), filter.getForTrade()));
            }

            if (filter.getForSale() != null) {
                predicates.add(cb.equal(root.get("isForSale"), filter.getForSale()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
	
}
