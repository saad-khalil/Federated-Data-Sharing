
<script lang="ts" setup>

    const props = defineProps({
        label: String
    })

    const emit = defineEmits(['change'])

    const input_value : Ref<string> = ref<string>("")
</script>

<template>
    <div class="w-full">
        <div
        class="mt-2 w-full">
            <label class="block text-neutral-300">{{ props.label?.toUpperCase() }}</label>
            <input
            type="text"
            name=""
            id=""
            v-model="input_value"
            @focusout="$emit('change', {
                label: label,
                value: input_value
            })"
            :placeholder="`Enter ${props.label}`"
            class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900" autofocus required>
        </div>
    </div>
</template>