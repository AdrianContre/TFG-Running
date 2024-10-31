import { MapContainer, TileLayer, useMap } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import "leaflet-gpx";
import { useEffect } from "react";

const GpxViewer = ({ gpxUrl }) => {
    const map = useMap();

    useEffect(() => {
        if (gpxUrl) {
            // Carga el archivo .gpx en el mapa
            new L.GPX(gpxUrl, {
                async: true,
                marker_options: {
                    startIconUrl: "https://leafletjs.com/examples/custom-icons/leaf-green.png",
                    endIconUrl: "https://leafletjs.com/examples/custom-icons/leaf-red.png",
                    shadowUrl: "https://leafletjs.com/examples/custom-icons/leaf-shadow.png",
                }
            }).on("loaded", (e) => {
                map.fitBounds(e.target.getBounds());
            }).addTo(map);
        }
    }, [gpxUrl, map]);

    return null;
};

export default GpxViewer;