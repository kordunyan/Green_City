import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ITrashExportation } from 'app/shared/model/trash-exportation.model';
import { getEntities as getTrashExportations } from 'app/entities/trash-exportation/trash-exportation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './full-trash-images.reducer';
import { IFullTrashImages } from 'app/shared/model/full-trash-images.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FullTrashImagesUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const trashExportations = useAppSelector(state => state.trashExportation.entities);
  const fullTrashImagesEntity = useAppSelector(state => state.fullTrashImages.entity);
  const loading = useAppSelector(state => state.fullTrashImages.loading);
  const updating = useAppSelector(state => state.fullTrashImages.updating);
  const updateSuccess = useAppSelector(state => state.fullTrashImages.updateSuccess);
  const handleClose = () => {
    props.history.push('/full-trash-images');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTrashExportations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...fullTrashImagesEntity,
      ...values,
      trashExportation: trashExportations.find(it => it.id.toString() === values.trashExportation.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...fullTrashImagesEntity,
          trashExportation: fullTrashImagesEntity?.trashExportation?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="greenCityApp.fullTrashImages.home.createOrEditLabel" data-cy="FullTrashImagesCreateUpdateHeading">
            Create or edit a FullTrashImages
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="full-trash-images-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Path" id="full-trash-images-path" name="path" data-cy="path" type="text" />
              <ValidatedField
                id="full-trash-images-trashExportation"
                name="trashExportation"
                data-cy="trashExportation"
                label="Trash Exportation"
                type="select"
              >
                <option value="" key="0" />
                {trashExportations
                  ? trashExportations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/full-trash-images" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FullTrashImagesUpdate;
