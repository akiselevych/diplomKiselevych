import { useState } from "react"
import PropTypes from "prop-types"
import { Avatar } from "@mui/material"
import Menu from "./Menu"
import MediaUpload from "../../../components/modals/MediaUpload";
import AvatarView from "../../../components/modals/AvatarView";
import styles from './styles.module.scss'
import diaIcon from './icons/diaIcon.svg'
import volunteerIcon from './icons/volunteer.svg'


const AvatarMenu = ({ avatarUrl, isVerified, isVolunteer }) => {
    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);

    const [avatarUploadModalOpen, setAvataUploadModalOpen] = useState(false);
    const [avatarViewModalOpen, setAvatarViewModalOpen] = useState(false);

    const toggleMenu = (event, actionType) => {
        if (actionType === 'upload') {
            setAvataUploadModalOpen(true);
        } else if (actionType === 'view') {
            setAvatarViewModalOpen(true);
        }

        if (open) {
            setAnchorEl(null)
        } else {
            setAnchorEl(event.currentTarget);
        }
    };

    const handleAvatarUploadModalClose = () => {
        setAvataUploadModalOpen(false);
    }

    const handleAvatarViewModalClose = () => {
        setAvatarViewModalOpen(false);
    }

    return (
        <>
            <div className={styles.avatar}>
                <Avatar onClick={toggleMenu} sx={{
                    width: '168px',
                    height: 'auto',
                    border: '5px solid white',
                    transitionProperty: 'scale',
                    transition: '0.2s ease-in-out',
                    cursor: 'pointer',
                    scale: open ? '0.95' : '1',
                    '& .MuiAvatar-img': {
                        transition: 'filter 0.2s ease-in-out',
                        filter: open ? 'brightness(0.8)' : 'brightness(1)',
                    },
                    ':hover': {
                        '& .MuiAvatar-img': {
                            filter: 'brightness(0.8)',
                        },
                        scale: '1.02'
                    }
                }} src={avatarUrl} />
                {isVerified && <img src={diaIcon} alt="diaIcon" className={styles.diaIcon}/>}
                {isVolunteer && <img src={volunteerIcon} alt="diaIcon" className={styles.volunteerIcon}/>}
            </div>

            <Menu open={open} anchorEl={anchorEl} onClose={toggleMenu} />
            <MediaUpload onClose={handleAvatarUploadModalClose} open={avatarUploadModalOpen} modalTitle="Upload a new avatar" />
            <AvatarView onClose={handleAvatarViewModalClose} open={avatarViewModalOpen} />
        </>
    )
}

AvatarMenu.propTypes = {
    avatarUrl: PropTypes.string,
    isVerified: PropTypes.bool,
    isVolunteer: PropTypes.bool,
}

export default AvatarMenu