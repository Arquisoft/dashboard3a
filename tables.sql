CREATE TABLE TCategories (
  ID IDENTITY,
  NAME TEXT
);

CREATE TABLE TCitizens (
  ID IDENTITY,
  NAME TEXT,
  SURNAME TEXT,
  EMAIL TEXT,
  BIRTHDAY DATE,
  RESIDENCE TEXT,
  NATIONALITY TEXT,
  DNI TEXT
);

CREATE TABLE TComments (
  ID IDENTITY,
  CONTENTS TEXT,
  CREATIONDATE DATE,
  SUGGESTION_ID BIGINT FOREIGN KEY REFERENCES TSuggestions (ID),
  USER_ID BIGINT FOREIGN KEY REFERENCES TUsers (ID)
);

CREATE TABLE TCommentsVotes (
  COMMENT_ID BIGINT NOT NULL,
  USER_ID BIGINT NOT NULL,
  VOTE TEXT,
  CONSTRAINT PK_TCOMMENTVOTES PRIMARY KEY (COMMENT_ID, USER_ID),
  CONSTRAINT FK_TCOMMENTVOTES_COMMENT FOREIGN KEY (COMMENT_ID) REFERENCES TCOMMENTS (ID),
  CONSTRAINT FK_TCOMMENTVOTES_USER FOREIGN KEY (USER_ID) REFERENCES TUSERS (ID)
);

CREATE TABLE TRestringedWords (
  ID IDENTITY,
  WORD TEXT
);

CREATE TABLE TSuggestions (
  ID IDENTITY,
  CONTENTS TEXT,
  STATUS TEXT,
  CREATIONDATE DATE,
  FINALIZATIONDATE DATE,
  USER_ID BIGINT FOREIGN KEY REFERENCES TUSERS (ID),
  CATEGORY_ID BIGINT FOREIGN KEY REFERENCES TCATEGORIES (ID)
);

CREATE TABLE TSuggestionsVotes (
  SUGGESTION_ID BIGINT NOT NULL,
  USER_ID BIGINT NOT NULL,
  VOTE TEXT,
  CONSTRAINT PK_TSUGGESTIONSVOTES PRIMARY KEY (SUGGESTION_ID, USER_ID),
  CONSTRAINT FK_TSUGGESTIONSVOTES_SUGGESTION FOREIGN KEY (SUGGESTION_ID) REFERENCES TSUGGESTIONS (ID),
  CONSTRAINT FK_TSUGGESTIONSVOTES_USER FOREIGN KEY (USER_ID) REFERENCES TUSERS (ID)
);

CREATE TABLE TUsers (
  ID IDENTITY,
  USERNAME TEXT,
  PASSWORD TEXT,
  ISADMIN BOOLEAN,
  CITIZEN_ID BIGINT UNIQUE FOREIGN KEY REFERENCES TCITIZENS (ID)
);
