package com.wuda.foundation.item;

import lombok.Getter;

import java.util.Objects;

/**
 * 用于创建item.
 *
 * @author wuda
 * @since 1.0.0
 */
@Getter
public class CreateItemVariation {

    private Long itemId;
    private String name;
    private ItemState state;

    /**
     * 禁止实例化,使用{@link Builder}实例化.
     */
    private CreateItemVariation() {

    }

    /**
     * 用于创建{@link CreateItemVariation}实例.
     *
     * @author wuda
     * @since 1.0.0
     */
    public static class Builder implements com.wuda.foundation.lang.Builder<CreateItemVariation> {
        private Long itemId;
        private String name;
        private ItemState state;

        public Builder setItemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setState(ItemState state) {
            this.state = state;
            return this;
        }

        @Override
        public CreateItemVariation build() {
            CreateItemVariation createItemVariation = new CreateItemVariation();
            createItemVariation.itemId = Objects.requireNonNull(itemId);
            createItemVariation.name = Objects.requireNonNull(this.name);
            createItemVariation.state = Objects.requireNonNull(this.state);
            return createItemVariation;
        }
    }
}
