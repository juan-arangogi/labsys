/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { EntidadSaludLabsysDetailComponent } from 'app/entities/entidad-salud-labsys/entidad-salud-labsys-detail.component';
import { EntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';

describe('Component Tests', () => {
    describe('EntidadSaludLabsys Management Detail Component', () => {
        let comp: EntidadSaludLabsysDetailComponent;
        let fixture: ComponentFixture<EntidadSaludLabsysDetailComponent>;
        const route = ({ data: of({ entidadSalud: new EntidadSaludLabsys(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [EntidadSaludLabsysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EntidadSaludLabsysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EntidadSaludLabsysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.entidadSalud).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
