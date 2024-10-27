import { useState } from "react";
import { useEffect } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import { deleteMaterial, getUserMaterials } from "../services/materialService";
import MaterialCard from "./MaterialCard";
import '../styles/listMaterials.css'
import { Button } from "react-bootstrap";
import { useNavigate } from "react-router";

function ListMaterials () {

    const [materials,setMaterials] = useState([])

    const navigate = useNavigate()

    useEffect(() => {
        const id = JSON.parse(localStorage.getItem("userAuth")).id
        const getMaterialsUser = async () => {
            const userMaterials = await getUserMaterials(id);
            setMaterials(userMaterials)
        }
        getMaterialsUser()
    },[])

    const handleDeleteMaterial = async (materialId) => {
        await deleteMaterial(materialId);
        setMaterials(prevMaterials => prevMaterials.filter(material => material.id !== materialId))
    }

    const handleCreateMaterial = (event) => {
        event.preventDefault()
        navigate('/creatematerial')
    }


    return (
        <>
        <NavigationBar/>
        <div className="title-container">
            <h1>Materiales</h1>
        </div>
        <div className="material-list">
            {materials.map((material, index) => (
                <MaterialCard key={index} material={material} onDelete={handleDeleteMaterial} />
            ))}
        </div>
        <div className="title-container">
            <Button className="custom-material-button" variant="primary" size="lg" onClick={handleCreateMaterial}>Añadir material</Button>
        </div>
        </>
    )
}

export default ListMaterials;