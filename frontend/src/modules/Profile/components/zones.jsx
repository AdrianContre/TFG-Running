import '../styles/zones.css'

function Zones ({zones}) {
    console.log("zonas: " + zones)
    return (
        <div className="table-container-heart-zones">
            <table className="heart-rate-zones-table">
                <thead>
                    <tr>
                        <th>Zona</th>
                        <th>Pulsaciones</th>
                    </tr>
                </thead>
                
                <tbody>
                    <tr>
                        <td className="zone-label">Zona 1 {"(<70%)"}</td>
                        <td className="zone zone1">0-{zones.maxZ1} ppm</td>
                    </tr>
                    <tr>
                        <td className="zone-label">Zona 2 {"(70-80%)"}</td>
                        <td className="zone zone2">{zones.maxZ1 + 1}-{zones.maxZ2} ppm</td>
                    </tr>
                    <tr>
                        <td className="zone-label">Zona 3 {"(80-88%)"}</td>
                        <td className="zone zone3">{zones.maxZ2 + 1}-{zones.maxZ3} ppm</td>
                    </tr>
                    <tr>
                        <td className="zone-label">Zona 4 {"(88-92%)"}</td>
                        <td className="zone zone4">{zones.maxZ3 + 1}-{zones.maxZ4} ppm</td>
                    </tr>
                    <tr>
                        <td className="zone-label">Zona 5 {"(92-100%)"}</td>
                        <td className="zone zone5">&gt; {zones.maxZ4 + 1} ppm</td>
                    </tr>
                </tbody>
            </table>
        </div>
    )
    
}

export default Zones;