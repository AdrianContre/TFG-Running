import { useLocation, useNavigate } from "react-router";
import NavigationBar from "../../home/components/NavigationBar";
import RatingComponent from "./RatingComponent";
import { useState, useEffect } from "react";
import PopUp from "../../auth/components/PopUp";
import Button from 'react-bootstrap/Button';
import '../../activities/styles/createActivity.css'
import Select from "react-select";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { registerLocale} from  "react-datepicker";
import { es } from 'date-fns/locale/es';
import { getUserMaterials } from "../../profile/services/materialService";
import { createMobilitySessionResult, createStrengthSessionResult } from "../services/trainingResultService";
import { useSelector } from "react-redux";


function CreateSessionResult() {
    registerLocale('es', es)
    const location = useLocation()
    const {plan, session} = location.state
    const [description, setDescription] = useState('')
    const [rating, setRating] = useState(null);
    const [materials, setMaterials] = useState([]);
    const [selectedMaterials, setSelectedMaterials] = useState([]);
    const [date, setDate] = useState(new Date());
    const [error, setError] = useState("")
    const [title,setTitle] = useState("")
    const [show, setShow] = useState(false)
    const navigate = useNavigate()

    const userAuth = useSelector((state) =>{return state.auth.user})

    const updateValue = (setter) => (event) => { 
        setter(event.target.value);
    };

    const handleRatingChange = (value) => {
        setRating(value);
    };

    const materialOptions = materials.map(material => ({
        value: material.id, 
        label: `${material.brand} ${material.model}`
    }));

    const handleHide = (event) => {
        event.preventDefault();
        setShow(false);
    };

    useEffect(() => {
        const getMaterials = async () => {
            const userMaterials = await getUserMaterials(userAuth.id);
            if (userMaterials.error) {
                setShow(true);
            } else { 
                setMaterials(userMaterials);
            }
        };
        getMaterials();
    },[])

    const handleSendActivity = async (event) => {
        event.preventDefault();
        let materialsId = []
        selectedMaterials.forEach((material) => {
            materialsId.push(material.value)
        })
        const dataInfo = date.toISOString()
        if (description == "" || materialsId.length == 0 || date == "" || rating == null) {
            setShow(true)
            setError("Todos los campos son obligatorios")
            setTitle("Error al crear el resultado")
        }
        else {
            try {
                if (session.type === "strength") {
                    const activity = await createStrengthSessionResult(plan.id, userAuth.id, session.id, description, rating, materialsId, dataInfo)
                }
                else {
                    const activity = await createMobilitySessionResult(plan.id, userAuth.id, session.id, description, rating, materialsId, dataInfo)
                }
                
                navigate('/viewplan', { state: {planId: plan.id}})
            }
            catch (Error) {
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
                    {session.type === "strength" ? (<h1 className="custom-h1">Añadir resultado sesión de fuerza</h1>) : 
                    (<h1 className="custom-h1">Añadir resultado sesión de movilidad</h1>)
                    }
                    <div>
                        <label className='custom-label-createact' htmlFor="brand">PLAN ASOCIADO</label>
                        <input name='brand' value={plan.name} className='custom-input-createact' disabled/>

                        <label className='custom-label-createact' htmlFor="brand">NOMBRE</label>
                        <input name='brand' value={session.name}  className='custom-input-createact' disabled/>

                        <label className='custom-label-createact' htmlFor="description">DESCRIPCIÓN</label>
                                <textarea name='description' value={description} onChange={updateValue(setDescription)} className='custom-textarea-createact' />

                        <label className='custom-label-createact'>COMO TE SIENTES?</label>
                        <RatingComponent onChange={handleRatingChange} />
                        
                        <label className='custom-label-createact' htmlFor="materials">MATERIALES</label>
                        <Select
                            isMulti
                            options={materialOptions}
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
                    <Button variant='primary' size='lg' className='mt-5 custom-button-createact' onClick={handleSendActivity}>AÑADIR</Button>
                </div>
            </div>
            <PopUp error={error} show={show} onHide={handleHide} title={title}/>
        </>
    )
}

export default CreateSessionResult;