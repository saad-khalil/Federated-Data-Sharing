<script lang="ts" setup>
import "leaflet/dist/leaflet.css";
import { LMap, LTileLayer, LMarker, LPopup } from "@vue-leaflet/vue-leaflet";
import { type PointExpression } from "leaflet";
import { createMarkerIcon } from "~/utils/iconFactory";
import { useOrganizationStore } from "~/stores/Organization/OrganizationStore";
import { Inventory } from "~/types/Inventory/Inventory";

definePageMeta({
  middleware: "auth",
});

const organizationStore = useOrganizationStore();
const { getOrganizations } = organizationStore;

const isLoading = ref(true);

onMounted(async () => {
  setTimeout(async () => {
    await organizationStore.fetchOrganizations();
    isLoading.value = false;
  });
});

const showLocations = ref(Array(getOrganizations.value.length).fill(false));

const toggleLocations = (index: number) => {
  showLocations.value[index] = !showLocations.value[index];
};

const filteredLocations = (index: number) => {
  if (showLocations.value[index]) {
    return getOrganizations.value[index].locations;
  } else {
    return [];
  }
};

const mapLocations = computed(() => {
  const locations = [];
  for (let i = 0; i < getOrganizations.value.length; i++) {
    if (showLocations.value[i]) {
      for (const location of getOrganizations.value[i].locations) {
        locations.push({
          id: location.id,
          organizationName: getOrganizations.value[i].name,
          lat: location.latitude,
          lon: location.longitude,
          address: location.address,
          inventory: location.inventory,
        });
      }
    }
  }
  return locations;
});

let zoom = ref(7);
let center = ref<PointExpression>([52.1926, 5.2913]);

const calculateHighestCapacityPercentage = (inventory: Inventory[]) => {
  const inventoryPercentage= (inventory.reduce((highestPercentage, item) => {
    const percentage = (item.quantity / item.capacity) * 100;
    return Math.max(highestPercentage, percentage);
  }, 0))
  return Math.round(inventoryPercentage*10) / 10 ;
};
</script>

<template>
  <div
    class="max-h-[calc(100vh-4rem)] w-full flex overflow-hidden select-none pl-4 pr-4"
  >
    <div
      class="mr-6 w-2/3 pt-8 pb-2 flex-shrink-0 flex flex-col h-[calc(100vh-4rem)] text-white"
    >
      <div
        class="bg-neutral-800 h-full w-full border-2 border-transparent hover:border-emerald-500 transition duration-500 rounded-lg"
      >
        <l-map
          ref="map"
          v-model:zoom="zoom"
          v-model:center="center"
          class="h-100 w-100 relative"
          :useGlobalLeaflet="false"
        >
          <l-tile-layer
            url="https://tiles.stadiamaps.com/tiles/alidade_smooth_dark/{z}/{x}/{y}{r}.png"
            layer-type="base"
            name="Locations map"
          ></l-tile-layer
          ><l-marker
            v-for="(location, locationIndex) in mapLocations"
            :key="locationIndex"
            :lat-lng="[parseFloat(location.lat), parseFloat(location.lon)]"
            :icon="
              createMarkerIcon(
                calculateHighestCapacityPercentage(location.inventory)
              )
            "
          >
            <l-popup>
              <div>
                <strong>{{ location.organizationName }}</strong>
              </div>
              <div>{{ location.address }}</div>
              <div>
                Highest item capacity usage:
                {{ calculateHighestCapacityPercentage(location.inventory) }}
              </div>
              <NuxtLink :to="'location/' + location.id" class="underline"
                >Location details</NuxtLink
              >
            </l-popup>
          </l-marker>
        </l-map>
      </div>
    </div>
    <div
      class="w-1/3 pt-8 pb-2 flex-shrink-0 flex flex-col h-[calc(100vh-4rem)] bg-neutral-900 rounded-l-lg transition duration-500 ease-in-out overflow-y-auto"
    >
      <div
        class="mr-6 py-1 flex flex-col bg-neutral-800 border-2 border-transparent hover:border-emerald-500 transition duration-500 rounded-lg"
      >
        <div>
          <ul class="pt-1 pb-2 px-3 overflow-y-auto">
            <div v-if="isLoading" class="text-white">Loading...</div>
            <div class="text-white" v-else>
              <li
                class="mt-2"
                v-for="(organization, index) in getOrganizations"
                :key="index"
              >
                <a
                  class="p-5 flex flex-col bg-neutral-100 rounded-lg"
                  href="#"
                  @click="toggleLocations(index)"
                >
                  <div
                    class="flex items-center justify-between font-semibold capitalize text-gray-700"
                  >
                    <span>{{ organization.name }}</span>
                    <span>{{
                      showLocations[index] ? "Hide Locations" : "Show Locations"
                    }}</span>
                  </div>
                </a>
                <ul>
                  <li
                    v-for="(location, locationIndex) in filteredLocations(
                      index
                    )"
                    :key="locationIndex"
                    class="mt-2"
                  >
                    <ul
                      class="p-5 flex flex-col bg-neutral-900 rounded-lg text-white"
                    >
                      <li>{{ location.address }}</li>
                      <li>
                        <NuxtLink
                          :to="'location/' + location.id"
                          class="underline"
                          >Location details</NuxtLink
                        >
                      </li>
                      <ul class="p-3 mt-2 bg-neutral-800 rounded-lg text-white">
                        <li
                          v-for="(
                            inventory, inventoryIndex
                          ) in location.inventory"
                          :key="inventoryIndex"
                          class="mt-2 p-2 bg-neutral-700 rounded-lg"
                        >
                          Item: {{ inventory.itemType }} <br />
                          Quantity: {{ inventory.quantity }}
                        </li>
                      </ul>
                    </ul>
                  </li>
                </ul>
              </li>
            </div>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>
