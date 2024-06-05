import s from "../RegisterConfirmModal/RegisterConfirmModal.module.scss";
import styles from './styles.module.scss'
import {Modal} from "@mui/material";
import {useForm} from "react-hook-form";
import {useDispatch, useSelector} from "react-redux";
import {createFundraising} from "../../../store/slices/Volunteer.slice.jsx";
import {useEffect} from "react";


const CreateFundraisingModal = ({open, onClose}) => {

    const {register, handleSubmit, formState: {isValid}} = useForm()
    const dispatch = useDispatch()
    const createStatus = useSelector(state => state.volunteers.createStatus)

    const onSubmit = (data) => {
        dispatch(createFundraising(data));
    }

    useEffect(() => {
        if (createStatus === 'idle'){
            onClose();
        }

        //eslint-disable-next-line
    }, [createStatus]);


    return (
        <Modal open={open} onClose={onClose} className={s.modalInner}
               disableAutoFocus>
            <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                <div className={styles.inputField}>
                    <label htmlFor="name">Назва збору: </label>
                    <input type="text" {...register('name', {required: true})}/>
                </div>
                <div className={styles.inputField}>
                    <label htmlFor="name">Опис: </label>
                    <textarea cols={24} rows={6} {...register('textContent', {required: true})}/>
                </div>
                <div className={styles.inputField}>
                    <label htmlFor="name">Грошова ціль: </label>
                    <input type="number" {...register('finalAmount', {required: true, min: 1000})}/>
                </div>
                <button type={"submit"} className={styles.submitBtn} disabled={!isValid}>Зберегти</button>
            </form>
        </Modal>
    );
};

export default CreateFundraisingModal;
