import React from "react";
import { useEffect, useState } from "react";
import NavigationBar from "../../home/components/NavigationBar";
import PopUp from "../../auth/components/PopUp";
import { useNavigate } from "react-router";
import { useLocation } from "react-router";
import Button from 'react-bootstrap/Button';
import { editMaterial, uploadPhoto } from "../services/materialService";
import { data } from "@remix-run/router";

function EditMaterial () {
    const location = useLocation()
    const {material} = location.state
    const [brand, setBrand] = useState("")
    const [model, setModel] = useState("")
    const [description, setDescription] = useState("")
    const [wear, setWear] = useState(0)
    const [show, setShow] = useState(false)
    const [picture, setPicture] = useState(null)
    const navigate = useNavigate()

    const updateValue = (setter) => (event) => { 
        setter(event.target.value);
    } 

    const handleHide = (event) => {
        event.preventDefault()
        setShow(false)
    }

    const handleEditMaterial = async () => {
        if (brand === "" || model === "" || description === "" || wear === "") {
            setShow(true)
        }
        else {
            const result = await editMaterial(brand, model, description, wear, material.id)
            if (result.error) {
                console.log("Error")
            }
            else {
                if (picture !== null) {
                    const formData = new FormData()
                    formData.append('photo', picture)
                    console.log(formData)
                    const upload = await uploadPhoto(material.id, formData)

                }
                
                navigate('/listmaterials')
            }
        }
    }

    useEffect(() => {
        console.log(material)
        setBrand(material.brand)
        setModel(material.model)
        setDescription(material.description)
        setWear(material.wear)
    },[])

    return (
        <>
            <NavigationBar />
            <div className='d-flex align-items-center justify-content-center' style={{marginTop: '5%'}}> 
                <div className='container-createmat d-grid d-flex align-items-center'>
                    <h1 className="custom-h1">Añadir material</h1>
                    <div className='container-createmat d-grid d-flex align-items-center' style={{marginTop: '50px',}}>
                        <label className='custom-label-createmat' htmlFor="brand">MARCA</label>
                        <input name='brand' value={brand} onChange={updateValue(setBrand)} className='custom-input-register' ></input>
                        <label className='custom-label-createmat' htmlFor="model">MODELO</label>
                        <input name='model' value={model} onChange={updateValue(setModel)} className='custom-input-register'></input>
                        <label className='custom-label-createmat' htmlFor="description">DESCRIPCIÓN</label>
                        <textarea name='description' value={description} onChange={updateValue(setDescription)} className='custom-input-register'/>
                        <label className='custom-label-createmat' htmlFor="wear">KILOMETRAJE {'(KM)'}</label>
                        <input name='wear' value={wear} type={"number"} step="0.01" onChange={updateValue(setWear)} className='custom-input-createmat'></input>
                        <label className='custom-label-createmat' htmlFor="picture">Imagen {'(opcional)'}</label>
                        <input name='picture'  type="file"  onChange={(e) => setPicture(e.target.files[0])} className='custom-input-createmat' accept=".png, .jpg, .jpeg"></input>
                        <Button variant='primary' size='lg' className='mt-5 custom-button-createmat' onClick={handleEditMaterial}>EDITAR</Button>
                    </div>
                </div>
            </div>
            <PopUp error={"Se requiere enviar todos los parámetros"} show={show} onHide={handleHide} title={"Error al editar material"}/>
        </>
    )
}

export default EditMaterial;