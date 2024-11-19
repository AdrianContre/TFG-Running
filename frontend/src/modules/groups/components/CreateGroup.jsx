import React, { useEffect, useState } from "react";
import Select from "react-select";
import NavigationBar from "../../home/components/NavigationBar";
import "../styles/createGroup.css";
import { Button } from "react-bootstrap";
import { createGroup, getAllUsers } from "../services/groupService";
import PopUp from "../../auth/components/PopUp";
import { useNavigate } from "react-router";

function CreateGroup() {
    const [name, setName] = useState("")
    const [description, setDescription] = useState("")
    const [userOptions, setUserOptions] = useState([]);
    const [filteredOptions, setFilteredOptions] = useState([]);
    const [selectedUsers, setSelectedUsers] = useState([]);
    const [searchInput, setSearchInput] = useState(""); 
    const [show, setShow] = useState(false)
    const [error, setError] = useState("")

    const navigate = useNavigate()

    useEffect(() => {
        const fetchUsers = async () => {
            const users = await getAllUsers();
            const options = users.data.map((user) => ({
                value: user.id,
                label: user.username,
            }));
            setUserOptions(options);
        };
        fetchUsers();
    }, []);

    const handleInputChange = (inputValue) => {
        setSearchInput(inputValue);
    
        if (inputValue) {
            const filtered = userOptions.filter((option) => {
                const regex = new RegExp(`^${inputValue.toLowerCase()}`);
                return regex.test(option.label.toLowerCase());
            });
            setFilteredOptions(filtered);
        } else {
            setFilteredOptions([]);
        }
    };

    const handleCreateGroup = async (event) => {
        event.preventDefault()
        let runnersId = []
        const trainerId = JSON.parse(localStorage.getItem('userAuth')).id
        selectedUsers.map(user => {
            runnersId.push(user.value)
        })
        if (runnersId.length == 0 || name === "" || description === "") {
            setError("Todos los campos han de estar llenos, añadiendo almenos un usuario")
        }
        try {
            const send = await createGroup(name, description, trainerId, runnersId)
            navigate('groups')
        }
        catch (Error) {
            if (Error.status === 409) {
                setError(`El grupo con el nombre "${name}" ya existe`);
            }
            setShow(true)
            
        }
        
    }

    const onHide = (event) => {
        event.preventDefault()
        setShow(false)
    }

    return (
        <>
            <NavigationBar />
            <h1 className="custom-h1">Crear grupo:</h1>
            <div className="create-group-container">
                <div className="form-container-create-group">
                    <div className="form-group-create-group">
                        <label className="custom-label-create-group">Nombre:</label>
                        <input type="text" className="custom-input-create-group" value={name} onChange={(e) => setName(e.target.value)}/>
                    </div>
                    <div className="form-group-create-plan">
                        <label className='custom-label-create-group'>Descripción:</label>
                        <textarea className='custom-textarea-create-group' value={description} onChange={(e) => setDescription(e.target.value)} />
                    </div>
                    <div className="form-group-create-group">
                        <label className="custom-label-create-group">Añadir usuarios:</label>
                        <Select
                            isMulti
                            options={filteredOptions}
                            onChange={(selected) => setSelectedUsers(selected)}
                            onInputChange={handleInputChange} 
                            placeholder="Escribe para buscar usuarios..."
                            className="custom-select-creategroup"
                            noOptionsMessage={() =>
                                searchInput
                                    ? "No hay resultados"
                                    : "Escribe para buscar usuarios"
                            }
                        />
                    </div>
                </div>
            </div>
            <div style={{ display: "flex", justifyContent: "center" }}>
                <Button variant="primary" size="lg" className="mt-5" onClick={handleCreateGroup}>
                    CREAR GRUPO
                </Button>
            </div>
            <PopUp error={error} show={show} onHide={onHide} title={"Error al crear grupo"}/>
        </>
    );
}

export default CreateGroup;
