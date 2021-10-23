import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FullTrashImages from './full-trash-images';
import FullTrashImagesDetail from './full-trash-images-detail';
import FullTrashImagesUpdate from './full-trash-images-update';
import FullTrashImagesDeleteDialog from './full-trash-images-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FullTrashImagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FullTrashImagesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FullTrashImagesDetail} />
      <ErrorBoundaryRoute path={match.url} component={FullTrashImages} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FullTrashImagesDeleteDialog} />
  </>
);

export default Routes;
