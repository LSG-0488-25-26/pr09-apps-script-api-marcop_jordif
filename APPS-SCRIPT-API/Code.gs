// CONFIGURACIÓ INICIAL
const API_KEYS = ['dokkan1919'];
const CARDS_SHEET = 'cards';
const COMMENTS_SHEET = 'user_comments';

// VALIDACIÓ API KEY
function validateApiKey(apiKey) {
  return API_KEYS.includes(apiKey);
}

// FUNCIÓ PER OBTENIR ELS HEADERS AUTOMÀTICAMENT
function getSheetHeaders(sheet) {
  const data = sheet.getDataRange().getValues();
  return data.length > 0 ? data[0] : [];
}

// FUNCIÓ PER CONVERTIR FILES A JSON AUTOMÀTICAMENT
function rowsToJson(rows, headers) {
  const jsonData = [];
  for (let i = 1; i < rows.length; i++) {
    const row = {};
    for (let j = 0; j < headers.length; j++) {
      row[headers[j]] = rows[i][j];
    }
    jsonData.push(row);
  }
  return jsonData;
}

// FUNCIÓ PER TROBAR L'ÍNDEX D'UNA COLUMNA PER NOM
function findColumnIndex(headers, columnName) {
  for (let i = 0; i < headers.length; i++) {
    if (headers[i] && headers[i].toLowerCase().includes(columnName.toLowerCase())) {
      return i;
    }
  }
  return -1;
}

// DOGET - PER CONSULTAR DADES
function doGet(e) {
  try {
    // Verificar API Key
    const apiKey = e.parameter.api_key;
    if (!validateApiKey(apiKey)) {
      return ContentService
        .createTextOutput(JSON.stringify({ error: 'API Key invàlida' }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    const action = e.parameter.action;
    const ss = SpreadsheetApp.getActiveSpreadsheet();
    const cardsSheet = ss.getSheetByName(CARDS_SHEET);
    
    if (!cardsSheet) {
      return ContentService
        .createTextOutput(JSON.stringify({ error: 'No s\'ha trobat la pestanya cards' }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    const cardsData = cardsSheet.getDataRange().getValues();
    const cardsHeaders = cardsData[0];
    
    // ENDPOINT 1: Obtenir totes les cartes
    if (action === 'getAllCards') {
      const jsonData = rowsToJson(cardsData, cardsHeaders);
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: true, 
          count: jsonData.length,
          data: jsonData 
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    // ENDPOINT 2: Obtenir carta per ID (busca automàticament la columna ID)
    if (action === 'getCardById') {
      const searchId = e.parameter.id;
      if (!searchId) {
        return ContentService
          .createTextOutput(JSON.stringify({ error: 'Falta el paràmetre id' }))
          .setMimeType(ContentService.MimeType.JSON);
      }
      
      // Buscar quina columna pot ser l'ID
      let idColumnIndex = -1;
      for (let i = 0; i < cardsHeaders.length; i++) {
        const header = cardsHeaders[i].toString().toLowerCase();
        if (header.includes('id') || header.includes('number') || header.includes('num')) {
          idColumnIndex = i;
          break;
        }
      }
      
      // Si no troba columna ID, usa la primera columna
      if (idColumnIndex === -1) idColumnIndex = 0;
      
      let found = null;
      for (let i = 1; i < cardsData.length; i++) {
        if (cardsData[i][idColumnIndex].toString() === searchId.toString()) {
          const row = {};
          for (let j = 0; j < cardsHeaders.length; j++) {
            row[cardsHeaders[j]] = cardsData[i][j];
          }
          found = row;
          break;
        }
      }
      
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: true, 
          data: found 
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    // ENDPOINT 3: Filtrar per tipus (STR, AGL, TEQ, INT, PHY)
    if (action === 'getCardsByType') {
      const searchType = e.parameter.type;
      if (!searchType) {
        return ContentService
          .createTextOutput(JSON.stringify({ error: 'Falta el paràmetre type' }))
          .setMimeType(ContentService.MimeType.JSON);
      }
      
      // Buscar quina columna pot ser el tipus
      let typeColumnIndex = -1;
      for (let i = 0; i < cardsHeaders.length; i++) {
        const header = cardsHeaders[i].toString().toLowerCase();
        if (header.includes('type') || header.includes('tipus') || header.includes('element')) {
          typeColumnIndex = i;
          break;
        }
      }
      
      // Si no troba columna tipus, intenta amb la columna 5 (comú a molts CSVs)
      if (typeColumnIndex === -1) typeColumnIndex = Math.min(5, cardsHeaders.length - 1);
      
      const jsonData = [];
      for (let i = 1; i < cardsData.length; i++) {
        const cellValue = cardsData[i][typeColumnIndex].toString().toUpperCase();
        if (cellValue.includes(searchType.toUpperCase())) {
          const row = {};
          for (let j = 0; j < cardsHeaders.length; j++) {
            row[cardsHeaders[j]] = cardsData[i][j];
          }
          jsonData.push(row);
        }
      }
      
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: true, 
          count: jsonData.length,
          data: jsonData 
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    // ENDPOINT 4: Filtrar per nom de personatge
    if (action === 'getCardsByCharacter') {
      const searchChar = e.parameter.character;
      if (!searchChar) {
        return ContentService
          .createTextOutput(JSON.stringify({ error: 'Falta el paràmetre character' }))
          .setMimeType(ContentService.MimeType.JSON);
      }
      
      // Buscar quina columna pot ser el personatge
      let charColumnIndex = -1;
      for (let i = 0; i < cardsHeaders.length; i++) {
        const header = cardsHeaders[i].toString().toLowerCase();
        if (header.includes('character') || header.includes('personatge') || header.includes('name')) {
          charColumnIndex = i;
          break;
        }
      }
      
      if (charColumnIndex === -1) charColumnIndex = 1; // Per defecte columna 1
      
      const jsonData = [];
      for (let i = 1; i < cardsData.length; i++) {
        const cellValue = cardsData[i][charColumnIndex].toString().toLowerCase();
        if (cellValue.includes(searchChar.toLowerCase())) {
          const row = {};
          for (let j = 0; j < cardsHeaders.length; j++) {
            row[cardsHeaders[j]] = cardsData[i][j];
          }
          jsonData.push(row);
        }
      }
      
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: true, 
          count: jsonData.length,
          data: jsonData 
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    // ENDPOINT 5: Obtenir comentaris
    if (action === 'getComments') {
      const commentsSheet = ss.getSheetByName(COMMENTS_SHEET);
      
      // Si la pestanya no existeix, la crea
      if (!commentsSheet) {
        const newSheet = ss.insertSheet(COMMENTS_SHEET);
        newSheet.appendRow(['id', 'card_id', 'user', 'comment', 'timestamp']);
        return ContentService
          .createTextOutput(JSON.stringify({ 
            success: true, 
            data: [], 
            message: 'Pestanya de comentaris creada' 
          }))
          .setMimeType(ContentService.MimeType.JSON);
      }
      
      const commentsData = commentsSheet.getDataRange().getValues();
      const commentsHeaders = commentsData[0];
      const jsonData = rowsToJson(commentsData, commentsHeaders);
      
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: true, 
          count: jsonData.length,
          data: jsonData 
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    // ENDPOINT 6: Obtenir estadístiques
    if (action === 'getStats') {
      const stats = {
        totalCards: cardsData.length - 1,
        totalColumns: cardsHeaders.length,
        headers: cardsHeaders,
        sampleRow: cardsData.length > 1 ? cardsData[1] : []
      };
      
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: true, 
          stats: stats 
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    // ENDPOINT 7: Obtenir estructura (per depuració)
    if (action === 'getStructure') {
      const sample = cardsData.length > 1 ? cardsData[1] : [];
      const structure = {
        totalRows: cardsData.length,
        totalColumns: cardsHeaders.length,
        headers: cardsHeaders,
        firstRowSample: sample,
        columnTypes: cardsHeaders.map((h, i) => ({
          header: h,
          sampleValue: sample[i] !== undefined ? sample[i].toString() : '(buit)',
          type: typeof sample[i]
        }))
      };
      
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: true, 
          structure: structure 
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    return ContentService
      .createTextOutput(JSON.stringify({ error: 'Acció no vàlida' }))
      .setMimeType(ContentService.MimeType.JSON);
      
  } catch (error) {
    return ContentService
      .createTextOutput(JSON.stringify({ 
        error: 'Error del servidor: ' + error.toString() 
      }))
      .setMimeType(ContentService.MimeType.JSON);
  }
}

// DOPOST - PER AFEGIR DADES
function doPost(e) {
  try {
    // Verificar API Key
    const apiKey = e.parameter.api_key;
    if (!validateApiKey(apiKey)) {
      return ContentService
        .createTextOutput(JSON.stringify({ error: 'API Key invàlida' }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    const action = e.parameter.action;
    const ss = SpreadsheetApp.getActiveSpreadsheet();
    
    // POST 1: Afegir comentari
    if (action === 'addComment') {
      let commentsSheet = ss.getSheetByName(COMMENTS_SHEET);
      
      // Si la pestanya no existeix, la crea
      if (!commentsSheet) {
        commentsSheet = ss.insertSheet(COMMENTS_SHEET);
        commentsSheet.appendRow(['id', 'card_id', 'user', 'comment', 'timestamp']);
      }
      
      // Validar paràmetres
      const card_id = e.parameter.card_id || 'unknown';
      const user = e.parameter.user || 'anonymous';
      const comment = e.parameter.comment;
      
      if (!comment) {
        return ContentService
          .createTextOutput(JSON.stringify({ error: 'Falta el paràmetre comment' }))
          .setMimeType(ContentService.MimeType.JSON);
      }
      
      // Generar ID únic
      const id = new Date().getTime().toString();
      const timestamp = new Date().toISOString();
      
      // Afegir fila
      commentsSheet.appendRow([id, card_id, user, comment, timestamp]);
      
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: true, 
          message: 'Comentari afegit correctament',
          id: id
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    // POST 2: Afegir carta (endpoint extra)
    if (action === 'addCard') {
      // Aquest és un endpoint addicional per si vols ampliar
      // De moment retorna un missatge
      return ContentService
        .createTextOutput(JSON.stringify({ 
          success: false, 
          message: 'Funció no implementada - només lectura'
        }))
        .setMimeType(ContentService.MimeType.JSON);
    }
    
    return ContentService
      .createTextOutput(JSON.stringify({ error: 'Acció POST no vàlida' }))
      .setMimeType(ContentService.MimeType.JSON);
      
  } catch (error) {
    return ContentService
      .createTextOutput(JSON.stringify({ 
        error: 'Error del servidor: ' + error.toString() 
      }))
      .setMimeType(ContentService.MimeType.JSON);
  }
}
