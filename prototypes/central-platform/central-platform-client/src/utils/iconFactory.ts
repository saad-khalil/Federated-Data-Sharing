let L: any;

if (process.client) {
  import("leaflet").then((leaflet) => {
    L = leaflet.default;
  });
}

export function createMarkerIcon(itemCapacityUsage: number) {
  if (!L) {
    return null;
  }

  const color =
  itemCapacityUsage < 30
      ? "green"
      : itemCapacityUsage < 50
      ? "yellow"
      : itemCapacityUsage < 80
      ? "orange"
      : itemCapacityUsage < 100
      ? "red"
      : "black";

  const svgIcon = `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="32" viewBox="0 0 24 32">
    <path d="M12 2C6.478 2 2 6.478 2 12c0 8 10 18 10 18s10-10 10-18c0-5.522-4.478-10-10-10zm0 18c-2.209 0-4-1.791-4-4s1.791-4 4-4 4 1.791 4 4-1.791 4-4 4z" fill="${color}" stroke="white" stroke-width="2" />
  </svg>`;

  return L.divIcon({
    className: "custom-marker-icon",
    html: svgIcon,
    iconSize: [24, 32],
    iconAnchor: [12, 32],
  });
}
