import { useLocation, useNavigate } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import RatingComponent from "../../TrainingPlans/components/RatingComponent";
import { useState, useEffect } from "react";
import PopUp from "../../auth/components/PopUp";
import Button from 'react-bootstrap/Button';
import '../../activities/styles/createActivity.css'
import Select from "react-select";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale, setDefaultLocale } from  "react-datepicker";
import { es } from 'date-fns/locale/es';
import { getUserMaterials } from "../../profile/services/materialService";
import { createMobilitySessionResult, createStrengthSessionResult } from "../../TrainingPlans/services/trainingResultService";
import { editResult, getMobilityResult, getStrengthResult } from "../services/activitiesService";

function EditGenericResult() {
    registerLocale('es', es)
    const location = useLocation()
    const {sesionId, type} = location.state
    const [name, setName] = useState("")
    const [description, setDescription] = useState('')
    const [rating, setRating] = useState(null);
    const [materials, setMaterials] = useState([]);
    const [selectedMaterials, setSelectedMaterials] = useState([]);
    const [materialOptions, setMaterialOptions] = useState([])
    const [defaultMats, setDefault] = useState([])
    const [date, setDate] = useState(new Date());
    const [error, setError] = useState("")
    const [title,setTitle] = useState("")
    const [show, setShow] = useState(false)
    const navigate = useNavigate()

    const updateValue = (setter) => (event) => { 
        setter(event.target.value);
    };

    const handleRatingChange = (value) => {
        setRating(value);
    };



    const handleHide = (event) => {
        event.preventDefault();
        setShow(false);
    };

    useEffect(() => {
        const fetchInfo = async () => {
            console.log(type)
            let activity = null
            if (type === "StrengthResult") {
                activity = await getStrengthResult(sesionId);
            }
            else {
                activity = await getMobilityResult(sesionId);
            }
            setName(activity.data.name)
            setDescription(activity.data.description)
            setRating(activity.data.effort)
            setDate(new Date(activity.data.date))
            const runnerId = JSON.parse(localStorage.getItem("userAuth")).id;
            const userMaterials = await getUserMaterials(runnerId);
            if (userMaterials.error) {
                setShow(true);
            } else { 
                setMaterials(userMaterials);
            }
            const options = userMaterials.map(material => ({
                value: material.id, 
                label: `${material.brand} ${material.model}`
            }))
            setMaterialOptions(options)
            let selected = []
            activity.data.materials.forEach(material => {
                options.forEach(opt => {
                    if (opt.label === material) {
                        selected.push(opt)
                    }
                })
            })
            setSelectedMaterials(selected); 
        }
        fetchInfo()
    },[])

    const handleSendActivity = async (event) => {
        event.preventDefault();
        const runnerId = JSON.parse(localStorage.getItem("userAuth")).id
        let materialsId = []
        selectedMaterials.forEach((material) => {
            materialsId.push(material.value)
        })
        console.log(materialsId)
        const dataInfo = date.toISOString()
        if (description == "" || materialsId.length == 0 || date == "") {
            setShow(true)
            setError("Todos los campos son obligatorios")
            setTitle("Error al crear el resultado")
        }
        else {
            try {
                const activity = await editResult(sesionId,type, description, rating, dataInfo, materialsId, null, null, null, null)
                
                navigate('/activities')
            }
            catch (Error) {
                console.log(Error)
                setShow(true)
                setError("Revisa el formato de los campos del formulario")
                setTitle("Error al crear actividad")
            }
            
        }
        
    };

    return (
        <>
            <NavigationBar />
            <div className='d-flex align-items-center justify-content-center' style={{ marginTop: '5%' }}> 
                <div className='container-createact d-flex'>
                    {type === "StrengthResult" ? (<h1 className="custom-h1">Editar resultado sesión de fuerza</h1>) : 
                    (<h1 className="custom-h1">Editar resultado sesión de movilidad</h1>)
                    }
                    <div>

                        <label className='custom-label-createact' htmlFor="brand">NOMBRE</label>
                        <input name='brand' value={name}  className='custom-input-createact' disabled/>

                        <label className='custom-label-createact' htmlFor="description">DESCRIPCIÓN</label>
                                <textarea name='description' value={description} onChange={updateValue(setDescription)} className='custom-textarea-createact' />

                        <label className='custom-label-createact'>COMO TE SIENTES?</label>
                        <RatingComponent onChange={handleRatingChange} />
                        
                        <label className='custom-label-createact' htmlFor="materials">MATERIALES</label>
                            <Select
                                isMulti
                                options={materialOptions}
                                value={selectedMaterials} // Preselecciona los materiales de la actividad
                                onChange={(selected) => setSelectedMaterials(selected)}
                                className="custom-select-createact"
                            />
                        <div className="row" style={{marginTop: '20px'}}>
                            <label className='custom-label-createact' htmlFor="date">FECHA</label>
                            <DatePicker 
                                name="date"
                                selected={date} 
                                onChange={(date) => setDate(date)} 
                                dateFormat="dd/MM/yyyy"
                                className='custom-input-createact'
                                locale="es"
                            />
                        </div>
                    </div>
                    <Button variant='primary' size='lg' className='mt-5 custom-button-createact' onClick={handleSendActivity}>EDITAR</Button>
                </div>
            </div>
            <PopUp error={error} show={show} onHide={handleHide} title={title}/>
        </>
    )
}

export default EditGenericResult;