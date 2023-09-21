package satisfyu.vinery.block.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class GenericAdvancementTrigger extends SimpleCriterionTrigger<GenericAdvancementTrigger.Instance> {

    private final ResourceLocation id;
    private final Predicate<String> predicate;

    public GenericAdvancementTrigger(ResourceLocation id, Predicate<String> predicate) {
        this.id = id;
        this.predicate = predicate;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public Instance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        String condition = json.get("flag").getAsString();
        return new Instance(predicate, condition);
    }

    public void trigger(ServerPlayer playerEntity, ItemStack itemStack) {
        this.trigger(playerEntity, (instance) -> predicate.test(instance.condition));
    }

    protected class Instance extends AbstractCriterionTriggerInstance {
        private final String condition;

        public Instance(ContextAwarePredicate composite, String condition) {
            super(GenericAdvancementTrigger.this.id, composite);
            this.condition = condition;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext serializer) {
            JsonObject jsonobject = super.serializeToJson(serializer);
            jsonobject.addProperty("flag", this.condition);
            return jsonobject;
        }
    }
}
